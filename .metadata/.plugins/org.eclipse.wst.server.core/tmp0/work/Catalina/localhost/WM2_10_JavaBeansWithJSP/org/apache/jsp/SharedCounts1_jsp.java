/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.75
 * Generated at: 2017-05-12 14:28:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class SharedCounts1_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n");
      out.write("<!-- \r\n");
      out.write("Example of sharing beans. \r\n");
      out.write("-->\r\n");
      out.write("<HTML>\r\n");
      out.write("<HEAD>\r\n");
      out.write("<TITLE>Shared Access Counts: Page 1</TITLE>\r\n");
      out.write("<LINK REL=STYLESHEET HREF=\"JSP-Styles.css\" TYPE=\"text/css\">\r\n");
      out.write("</HEAD>\r\n");
      out.write("<BODY>\r\n");
      out.write("\t<TABLE BORDER=5 ALIGN=\"CENTER\">\r\n");
      out.write("\t\t<TR>\r\n");
      out.write("\t\t\t<TH CLASS=\"TITLE\">Shared Access Counts: Page 1\r\n");
      out.write("\t</TABLE>\r\n");
      out.write("\t<P>\r\n");
      out.write("\t\t");
      coreservlets.AccessCountBean counter = null;
      synchronized (application) {
        counter = (coreservlets.AccessCountBean) _jspx_page_context.getAttribute("counter", javax.servlet.jsp.PageContext.APPLICATION_SCOPE);
        if (counter == null){
          counter = new coreservlets.AccessCountBean();
          _jspx_page_context.setAttribute("counter", counter, javax.servlet.jsp.PageContext.APPLICATION_SCOPE);
          out.write("\r\n");
          out.write("\t\t\t");
          org.apache.jasper.runtime.JspRuntimeLibrary.introspecthelper(_jspx_page_context.findAttribute("counter"), "firstPage", "SharedCounts1.jsp", null, null, false);
          out.write("\r\n");
          out.write("\t\t");
        }
      }
      out.write("\r\n");
      out.write("\t\tOf SharedCounts1.jsp (this page), <A HREF=\"SharedCounts2.jsp\">SharedCounts2.jsp</A>,\r\n");
      out.write("\t\tand <A HREF=\"SharedCounts3.jsp\">SharedCounts3.jsp</A>,\r\n");
      out.write("\t\t");
      out.write(org.apache.jasper.runtime.JspRuntimeLibrary.toString((((coreservlets.AccessCountBean)_jspx_page_context.findAttribute("counter")).getFirstPage())));
      out.write("\r\n");
      out.write("\t\twas the first page accessed.\r\n");
      out.write("\t<P>\r\n");
      out.write("\t\tCollectively, the three pages have been accessed\r\n");
      out.write("\t\t");
      out.write(org.apache.jasper.runtime.JspRuntimeLibrary.toString((((coreservlets.AccessCountBean)_jspx_page_context.findAttribute("counter")).getAccessCount())));
      out.write("\r\n");
      out.write("\t\ttimes.\r\n");
      out.write("\t\t");
      org.apache.jasper.runtime.JspRuntimeLibrary.introspecthelper(_jspx_page_context.findAttribute("counter"), "accessCountIncrement", "1", null, null, false);
      out.write("\r\n");
      out.write("</BODY>\r\n");
      out.write("</HTML>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
