package com.boco.eoms.base.util;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @description: 获取客户端IP地址
 * @author: paulandcode
 * @email: paulandcode@gmail.com
 * @since: 2018年9月17日 下午3:44:51
 */
public class IPUtils {

    private static Logger logger = LoggerFactory.getLogger(IPUtils.class);

    /**
     * 使用单例模式创建GeoLite2 数据库
     */
    private static DatabaseReader reader = null;

    /**
     *
     * @description: 获得国家
     * @param reader
     * @param ip
     * @return
     * @throws Exception
     */
    public static String getCountry(DatabaseReader reader, String ip) {
        try {
            return reader.city(InetAddress.getByName(ip)).getCountry().getNames().get("zh-CN");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ip+"获得国家报错",e);
            return null;
        }
    }

    /**
     *
     * @description: 获得省份
     * @param reader
     * @param ip
     * @return
     * @throws Exception
     */
    public static String getProvince(DatabaseReader reader, String ip) {
        try {
            return reader.city(InetAddress.getByName(ip)).getMostSpecificSubdivision().getNames().get("zh-CN");
        } catch (Exception e) {
            logger.error(ip+"获取省份报错",e);
            return null;
        }
    }

    /**
     *
     * @description: 获得城市
     * @param reader
     * @param ip
     * @return
     * @throws Exception
     */
    public static String getCity(DatabaseReader reader, String ip) throws Exception {
        try {
            return reader.city(InetAddress.getByName(ip)).getCity().getNames().get("zh-CN");
        } catch (Exception e) {
            logger.error(ip+"获得城市报错",e);
            return null;
        }
    }

    /**
     * 获取DatabaseReader用于读取地域信息,保证内存有唯一实例且一直存在可用
     * @return
     */
    public static DatabaseReader getDatabaseReader(String filePath) throws Exception{
        if(reader==null){
            synchronized(IPUtils.class){
                // 创建 GeoLite2 数据库
                File database = new File(filePath);
                // 读取数据库内容
                reader = new DatabaseReader.Builder(database).build();
            }
        }
        return reader;
    }

    /**
     * 用于判断ip是否为内网
     * tcp/ip协议中，专门保留了三个IP地址区域作为私有地址，其地址范围如下：
     * 10.0.0.0/8：10.0.0.0～10.255.255.255
     * 172.16.0.0/12：172.16.0.0～172.31.255.255
     * 192.168.0.0/16：192.168.0.0～192.168.255.255
     * @param ip
     * @return
     */
    public static boolean isInner(String ip){
        Pattern reg = Pattern.compile("^(127\\.0\\.0\\.1)|(localhost)|(10\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})|(172\\.((1[6-9])|(2\\d)|(3[01]))\\.\\d{1,3}\\.\\d{1,3})|(192\\.168\\.\\d{1,3}\\.\\d{1,3})$");
        Matcher match = reg.matcher(ip);
        return match.find();
    }

    public static void main(String[] args) {
        try {
            String ip= "219.143.153.244";
            System.out.println(isInner(ip));
            DatabaseReader databaseReader = IPUtils.getDatabaseReader("D:/temp/GeoLite2-City_20191217/GeoLite2-City.mmdb");
            System.out.println(IPUtils.getCountry(databaseReader,ip));
            System.out.println(IPUtils.getProvince(databaseReader,ip));
            System.out.println(IPUtils.getCity(databaseReader,ip));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}