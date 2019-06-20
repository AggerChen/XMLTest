#### XML概念：
XML 可扩展标记语言（Extensible Markup Language）,标签都是自定义的。开始创造出来是用来替代html的语言，但是后来主要用于存储数据，替代properties。例如配置文件，网络传输等。

#### XML与HTML区别:
1. xml标签是自定义的，html标签是预定义的
2. xml语法严格，html语法松散
3. xml是存储数据，html是用来展示数据

#### XML语法：
1. xml文档后缀已.xml结尾
2. xml文档第一行必须写文档声明 
3. xml文档中有且只有一个根标签
4. 属性值必须使用引号引起来
5. 标签必须正确关闭
6. xml标签名称区分大小写

*示例*
```xml
<?xml version='1.0' ?>

<users>
    <user id="1">
        <name>哇哈哈</name>
        <age>12</age>
        <sex>male</sex>
    </user>

    <user id="1">
        <name>哇哈哈</name>
        <age>12</age>
        <sex>male</sex>
    </user>
</users>
```
#### XML组成
1. 文档声明
    `<?xml version='1.0' encoding='UTF-8'  ?>`
    * version : 版本号 必须属性
    * encoding : 编码方式 告诉解析引擎当前文档使用的字符集 不填默认IOS-9989-1
    * standalone : 是否独立 yes依赖其他文件 no不依赖其他文件
2. 指令(了解)
    结合css来控制xml标签显示样式，可以引入css文件
    `<?xml-stylesheet type=" text/css"  href="a.css" ?>`
3. 标签
   规则：
   * 名称可以包含字母、数字、其他符号等
   * 名称不能以数字或符号开头
   * 名称不能以xml开头
   * 名称中不能包含空格
4. 属性
    id属性表示唯一标识
5. 文本
    注意特殊字符转移用CDATA包含`<![CDATA[  a>b  ]]>`
    
#### XML约束
用来约束xml标签、属性、顺序、结构等
###### 1.  DTD：一种简单的约束
内部dtd：将约束规则定义在xml文档中
外部dtd：将约束定义在外部dtd文件中
    * 本地：<!DOCTYPE 根标签名 SYSTEM "dtd文件路径">
    * 网络：<!DOCTYPE 根标签名 PUBLIC "dtd文件名" "dtd文件的位置url">
```
<!ELEMENT students (student*) >
<!ELEMENT student (name,age,sex) >
<!ELEMENT name (#PCDATA)>
<!ELEMENT age (#PCDATA)>
<!ELEMENT sex (#PCDATA)>
<!ATTLIST student number ID #REQUIRED>
```
   - ELEMENT：用来定义标签，* 表示0次或多次 +1次或多次
   - ATTLIST：用来定义属性
   - #PDATA : 字符串类型
   - #REQUIRED：唯一
   
*示例:*
```xml
<?xml version='1.0' ?>

<!-- 外部-本地tdt -->
<!DOCTYPE students SYSTEM "student.dtd">

<!-- 内部tdt -->
<!DOCTYPE students [
    <!ELEMENT students (student*) >
    <!ELEMENT student (name,age,sex) >
    <!ELEMENT name (#PCDATA)>
    <!ELEMENT age (#PCDATA)>
    <!ELEMENT sex (#PCDATA)>
    <!ATTLIST student number ID #REQUIRED>
]>

<students>
    <student number="agger1">
        <name>哇哈哈</name>
        <age>12</age>
        <sex>male</sex>
    </student>

    <student number="agger2">
        <name>哇哈哈</name>
        <age>12</age>
        <sex>male</sex>
    </student>
</students>
```

###### 2.  Schema：一种复杂的约束
dtd约束只能约束标签，不能约束值。schema约束可以约束标签结构和值等。以.xsd结尾的文件

*示例：student.xsd*
```xml
<?xml version="1.0" ?>

<!--自定义schema约束-->
<xsd:schema
        xmlns="http://www.agger.com/xml"
        xmlns:xsd="http://www.w3.org/2001/XMLSchema"
        targetNamespace="http://www.agger.com/xml" elementFormDefault="qualified">

    <!--  定义了一个元素students  -->
    <xsd:element name="students" type="studentsType"/>

    <!--  自定义复合类型studentsType  -->
    <xsd:complexType name="studentsType">
        <!-- 按顺序出现 -->
        <xsd:sequence>
            <!--子元素student studentType类型 最小出现0次 最大出现不限制 -->
            <xsd:element name="student" type="studentType" minOccurs="0" maxOccurs="unbounded"/>
        </xsd:sequence>
    </xsd:complexType>

    <!--  自定义复合类型studentType  -->
    <xsd:complexType name="studentType">
        <!--按顺序出现-->
        <xsd:sequence>
            <!--name元素 string类型-->
            <xsd:element name="name" type="xsd:string"/>
            <!--age元素 ageType类型-->
            <xsd:element name="age" type="ageType"/>
            <!--sex元素 sexType类型-->
            <xsd:element name="sex" type="sexType"/>
        </xsd:sequence>
        <!--定义studentType的属性id  numberTypel类型 required必须且唯一-->
        <xsd:attribute name="id" type="idType" use="required"/>
    </xsd:complexType>

    <!--  自定义简单类型sexType  -->
    <xsd:simpleType name="sexType">
        <!--基本格式string-->
        <xsd:restriction base="xsd:string">
            <!--枚举值-->
            <xsd:enumeration value="male"/>
            <xsd:enumeration value="female"/>
        </xsd:restriction>
    </xsd:simpleType>

    <!--  自定义简单类型ageType  -->
    <xsd:simpleType name="ageType">
        <!--基本类型integer-->
        <xsd:restriction base="xsd:integer">
            <!--最小值0-->
            <xsd:minInclusive value="0"/>
            <xsd:maxInclusive value="256"/>
        </xsd:restriction>
    </xsd:simpleType>

    <!--  自定义简单类型idType  -->
    <xsd:simpleType name="idType">
        <!--基本类型string-->
        <xsd:restriction base="xsd:string">
            <!--正则模式：agger_开头,后面接4个数字-->
            <xsd:pattern value="agger_\d{4}"/>
        </xsd:restriction>
    </xsd:simpleType>

</xsd:schema>
    
```

*示例：student.xml*
使用时，在xml页面引入自定义约束
1. 填写xml文档根标签
2. 引入xsi前缀，xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
3. 引入xsd文件命名空间  xsi:schemaLocation="http://www.agger.com/xml student.xsd"
4.为每一个xsd约束声明一个前缀作为标识 xmlns="http://www.agger.com/xml"

```xml
<?xml version="1.0" encoding="utf-8" ?>
<students  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.agger.com/xml student.xsd"
          xmlns="http://www.agger.com/xml">

    <student id="100">
        <name>agger</name>
        <age>20</age>
        <sex>female</sex>
    </student>

    <student id="agger_1001">
        <name>张三</name>
        <age>21</age>
        <sex>male</sex>
    </student>

</students>
```

#### XML解析
将xml文档数据读取到内存中，或写入数据到xml文档中。持久化存储。
1. DOM解析：将标记语言一次性全部加载到内存中
    - 优点：操作方便，可对文档进行CRUD操作
    - 缺点：占内存（因为是将文档全部读取到内存）
2. SAX解析：逐行读取，基于事件驱动
    - 优点：基本不占内存
    - 缺点：只能读取，不能增删改
    
 ###### XMl常用解析器
 - JAXP：sun公司提供的解析器，支持dom和sax两种方式
 - DOM4J：一款优秀的解析器
 - Jsoup：jsoup是一款java的HTML解析器，同时也能解析xml
 - PULL ：安卓系统内置解析器，只支持sax方式 

###### Jsoup使用示例
```java
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

```
