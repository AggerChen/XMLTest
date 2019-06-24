package com.agger.servlet.setvletContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 下载器示例
 * @author
 */
@WebServlet("/downloadDemo")
public class DownloadDemo extends javax.servlet.http.HttpServlet {
    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        //1.获取文件名
        String fileName = request.getParameter("fileName");
        //2.获取服务上下文
        ServletContext context = this.getServletContext();
        //3.获取服务器真实路径
        String realPath = context.getRealPath("/source/" + fileName);
        //4.获取文件的类型
        String mimeType = context.getMimeType(fileName);
        //5.设置响应类型
        response.setContentType(mimeType);
        response.setCharacterEncoding("utf-8");
        //6.设置响应打开方式
        response.setHeader("content-disposition","attachment;filename=" + fileName);

        FileInputStream input = new FileInputStream(realPath);
        byte[] buffer = new byte[1024*8];
        int len = 0;
        ServletOutputStream out = response.getOutputStream();
        while ((len = input.read(buffer))!=-1){
            out.write(buffer,0,len);
        }
        input.close();

    }

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        this.doPost(request,response);
    }
}
