//package ptithcm.controller;
//
//import java.io.IOException;
//import java.rmi.ServerException;
//import java.util.HashMap;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletOutputStream;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.JasperRunManager;
//import net.sf.jasperreports.engine.util.JRLoader;
//import ptithcm.dao.ConnectorDao;
//
//@SuppressWarnings("serial")
//@WebServlet("ctnx/export")
//public class ReportCTNXServlet extends HttpServlet {
//
//	public ReportCTNXServlet() {
//		super();
//	}
//
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
////		try {
////			request.getRequestDispatcher("report/ctnx.jsp").forward(request, response);
////		} catch (ServletException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServerException, IOException {
//		gererateReport(request, response);
//	}
//
//	private void gererateReport(HttpServletRequest request, HttpServletResponse response)
//			throws ServerException, IOException {
//		String error = "";
//		String ngaybd = request.getParameter("NGAYBD").toString();
//		String ngaykt = request.getParameter("NGAYKT").toString();
//		String type = request.getParameter("TYPE");
//
//		String jasper = "WEB-INF/views/report/ctnx.jasper";
//
//		HashMap<String, Object> param = new HashMap<String, Object>();
//
//		if (type == "IMPORT") {
//			param.put("REPORT_TITLE", "Detail of Import");
//		} else if (type == "EXPORT") {
//			param.put("REPORT_TITLE", "Detail of Export");
//		}
//
//		param.put("NGAYBD", ngaybd);
//		param.put("NGAYKT", ngaykt);
//		param.put("TYPE", type);
//
//		byte[] bytes = null;
//
//		ServletContext context = getServletContext();
//
//		try {
//			JasperReport report = (JasperReport) JRLoader.loadObjectFromFile(context.getRealPath(jasper));
//			bytes = JasperRunManager.runReportToPdf(report, param, new ConnectorDao().getConnection());
//		} catch (JRException e) {
//			// TODO Auto-generated catch block
//			error = e.getMessage();
//		} finally {
//			if (bytes != null) {
//				response.setContentType("application/pdf");
//				response.setContentLength(bytes.length);
//				ServletOutputStream sos = response.getOutputStream();
//				sos.write(bytes);
//				sos.flush();
//				sos.close();
//			} else {
//				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
//				request.setAttribute("error", error);
//				try {
//					rd.forward(request, response);
//				} catch (ServletException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//}