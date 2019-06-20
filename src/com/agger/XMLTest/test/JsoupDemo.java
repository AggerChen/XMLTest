package com.agger.XMLTest.test;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;
import cn.wanghaomiao.xpath.model.JXNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Jsoup示例
 */

public class JsoupDemo {

    /**
     * 解析本地xml
     */
    @Test
    public void demo1(){
        try {
            String path = JsoupDemo.class.getClassLoader().getResource("./com/agger/XMLTest/schema/student.xml").getPath();
            Document dom = Jsoup.parse(new File(path),"utf-8");
            Elements es = dom.getElementsByTag("student");

            //打印student标签数量
            System.out.println(es.size());
            System.out.println("-------------------------");

            //打印第一个student标签id属性
            Element e = es.get(0);
            String id = e.attr("id");
            System.out.println(id);
            System.out.println("-------------------------");

            String html = e.html();
            System.out.println(html);
            System.out.println("-------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 解析网络地址
     */
    @Test
    public void demo2(){
        try {
            URL url = new URL("https://www.zhihu.com/signup?next=%2F");
            Document dom = Jsoup.parse(url,10000);
            System.out.println(dom);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析本地html字符串
     */
    @Test
    public void demo3(){
        String html = "<html><head><div><a url='www.baidu.com'>百度链接</a><b>文档</b></div><div></div></head></html>";
        Document dom = Jsoup.parse(html);
        System.out.println(dom);
    }


    /**
     * 快速选择器selector
     */
    @Test
    public void demo4(){
        try {
            String path = JsoupDemo.class.getClassLoader().getResource("./com/agger/XMLTest/schema/student.xml").getPath();
            Document dom = Jsoup.parse(new File(path),"utf-8");
            //获取student标签id值为agger_1001的子标签sex
            Elements e = dom.select("student[id='agger_1001']>sex");
            System.out.println(e);
            System.out.println("-------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * XPath快速选择器
     * 需要导入JsoupXpath包
     * 文档：http://www.w3school.com.cn/xpath/xpath_syntax.asp
     */
    @Test
    public void demo5(){
        try {
            String path = JsoupDemo.class.getClassLoader().getResource("./com/agger/XMLTest/schema/student.xml").getPath();
            Document dom = Jsoup.parse(new File(path),"utf-8");

            //1.通过document创建一个JXDocument
            JXDocument jxdom = new JXDocument(dom);
            //2.写入xpath语法快速查找student
            List <JXNode> jxNode = jxdom.selN("//student");
            System.out.println("-----------------------------student");
            System.out.println(jxNode);

            //查询所有student标签下的name标签
            List <JXNode> jxNode2 = jxdom.selN("//student/name");
            System.out.println("-----------------------------name");
            System.out.println(jxNode2);

            //查询所有student标签属性id值为agger_1000的标签
            List <JXNode> jxNode3 = jxdom.selN("//student[@id='agger_1000']");
            System.out.println("-----------------------------student[@id='agger_1000']");
            System.out.println(jxNode3);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (XpathSyntaxErrorException e) {
            e.printStackTrace();
        }
    }
}

