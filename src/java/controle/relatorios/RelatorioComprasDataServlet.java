package controle.relatorios;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import modelo.cliente.Cliente;
import modelo.cliente.ClienteModelo;
import modelo.compra.Compra;
import modelo.compra.CompraModelo;

public class RelatorioComprasDataServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Document doc = new Document();
        String arquivoPdf = "relatorio.pdf";
        String mensagem = "relatorioFalha";
        String dataInicialS = request.getParameter("dataInicial");
        String dataFinalS = request.getParameter("dataFinal");

        CompraModelo compraModelo = new CompraModelo();

        if (!dataInicialS.isEmpty() && !dataFinalS.isEmpty()) {
            try {
                java.util.Date dataInicial = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicialS);
                java.util.Date dataFinal = new SimpleDateFormat("yyyy-MM-dd").parse(dataFinalS);

                List<Compra> compras = compraModelo.obterComprasData(dataInicial.getTime(), dataFinal.getTime());

                PdfWriter.getInstance(doc, new FileOutputStream(arquivoPdf));
                doc.open();

                Paragraph p = new Paragraph("Compras por dia - De " + dataInicialS + " até " + dataFinalS);
                p.setAlignment(1);
                doc.add(p);
                p = new Paragraph("  ");
                doc.add(p);

                PdfPTable table = new PdfPTable(2);
                PdfPCell cel1 = new PdfPCell(new Paragraph("Data"));
                PdfPCell cel2 = new PdfPCell(new Paragraph("Valor total"));

                table.addCell(cel1);
                table.addCell(cel2);

                if (compras != null && !compras.isEmpty()) {
                    List<String> datas = new ArrayList<String>();

                    for (Compra compraAtual : compras) {
                        java.sql.Date dataAtual = new java.sql.Date(compraAtual.getData());
                        String dataAtualS = dataAtual.toString();
                        if (datas.indexOf(dataAtualS) == -1) {
                            datas.add(dataAtualS);
                        }
                    }

                    for (String data : datas) {
                        double valor = 0;
                        for (Compra compra : compras) {
                            java.sql.Date dataAtual = new java.sql.Date(compra.getData());
                            String dataAtualS = dataAtual.toString();
                            if (dataAtualS.equals(data)) {
                                valor += compra.getTotal();
                            }
                        }
                        cel1 = new PdfPCell(new Paragraph(data));
                        cel2 = new PdfPCell(new Paragraph("R$ " + valor + ""));

                        table.addCell(cel1);
                        table.addCell(cel2);
                    }
                }                
                doc.add(table);
                doc.close();
                Desktop.getDesktop().open(new File(arquivoPdf));
                
                mensagem = "relatorioSucesso";                 
            } catch (DocumentException ex) {
                Logger.getLogger(RelatorioCompraClienteServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RelatorioCompraClienteServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        request.setAttribute("mensagem", mensagem);
        RequestDispatcher dispatcher = request.getRequestDispatcher("RelatoriosAdmServlet");
        dispatcher.forward(request, response);
    }
}
