<%@page import = "net.sf.jasperreports.engine.design.JRDesignQuery"%>
<%@page import = "net.sf.jasperreports.engine.xml.JRXmlLoader"%>
<%@page import = "net.sf.jasperreports.engine.design.JasperDesign"%>
<%@page import = "net.sf.jasperreports.view.JasperViewer"%>
<%@page import = "net.sf.jasperreports.engine.JRException"%>
<%@page import = "net.sf.jasperreports.engine.JRResultSetDataSource"%>
<%@page import = "net.sf.jasperreports.engine.JasperCompileManager"%>
<%@page import = "net.sf.jasperreports.engine.JasperExportManager"%>
<%@page import = "net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import = "net.sf.jasperreports.engine.JasperPrint"%>
<%@page import = "net.sf.jasperreports.engine.JasperReport"%>
<%@page import = "net.sf.jasperreports.engine.data.JRCsvDataSource"%>
<%@page import = "net.sf.jasperreports.engine.export.ooxml.JRDocxExporter"%>
<%@page import = "net.sf.jasperreports.engine.export.JRPdfExporter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<%-- <%@ include file="/Purchase.jsp" %> --%>

<%@ page import="net.sf.jasperreports.engine.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.ParseException" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="java.io.OutputStream" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="net.sf.jasperreports.engine.util.*" %> 
<%@ page import="net.sf.jasperreports.engine.export.*" %>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="Java Web App">
	<meta name="author" content="An Vo">
	<title>PDF Report</title>
</head>
<body>
<% 
Connection conn = null;
/* String sear=(String)session.getAttribute("sea");
String cate=(String)session.getAttribute("cat");
String comp=(String)session.getAttribute("com");

System.out.println("1 is:"+sear);
System.out.println("2 is:"+cate);
System.out.println("3 is:"+comp); */

try 
{
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=INT14103", "sa", "1309");
    
    //F:\BG Ky Thuat Lap Trinh HDT\Final\eclipse-workspace\INT14103\src\main\webapp\WEB-INF\views\report
    String jrxmlFile ="F:/BG Ky Thuat Lap Trinh HDT/Final/eclipse-workspace/INT14103//src/main/webapp/WEB-INF/views/report/ddhkpn.jrxml";
    
    InputStream input = new FileInputStream(new File(jrxmlFile));
    JasperDesign jasperDesign = JRXmlLoader.load(input);

    System.out.println("Compiling Report Designs");
    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

    System.out.println("Creating JasperPrint Object");
    
    
    HashMap<String, Object> map = new HashMap<String, Object>();


    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,map,conn);
    byte bytes[] = new byte[10000]; 
    JRPdfExporter exporter = new JRPdfExporter();
    ByteArrayOutputStream Stream = new ByteArrayOutputStream(); 
    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint); 
    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Stream); 
    exporter.exportReport(); 
    System.out.println("Size of byte array:"+Stream.size()); 
    bytes = Stream.toByteArray(); 
    response.setContentType("application/pdf"); 
    System.out.println("After JasperPrint = 1"); 
    response.setContentLength(bytes.length); 
    System.out.println("After JasperPrint = 2"); 
    Stream.close(); 
    System.out.println("After JasperPrint = 3"); 

    OutputStream outputStream = response.getOutputStream(); 
    System.out.println("After JasperPrint = 4"); 
    outputStream.write(bytes, 0, bytes.length); 
    outputStream.flush(); 
    outputStream.close(); 

}
catch(Exception e) 
{e.printStackTrace();} 


%>
</body>
</html>