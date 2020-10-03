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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import modelo.cliente.Cliente;
import modelo.cliente.ClienteModelo;
import modelo.compra.CompraModelo;

public class RelatorioCompraClienteServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Document doc = new Document();
        String arquivoPdf = "relatorio.pdf";
        String mensagem = "relatorioFalha";
        String dataInicialS = request.getParameter("dataInicial");
        String dataFinalS = request.getParameter("dataFinal");

        CompraModelo compraModelo = new CompraModelo();
        ClienteModelo clienteModelo = new ClienteModelo();

        if (!dataInicialS.isEmpty() && !dataFinalS.isEmpty()) {
            try {
                Date dataInicial = new SimpleDateFormat("yyyy-MM-dd").parse(dataInicialS);
                Date dataFinal = new SimpleDateFormat("yyyy-MM-dd").parse(dataFinalS);
                List<Cliente> clientes = clienteModelo.obterTodos();
                PdfWriter.getInstance(doc, new FileOutputStream(arquivoPdf));
                doc.open();

                Paragraph p = new Paragraph("Compras por Cliente");
                p.setAlignment(1);
                doc.add(p);
                p = new Paragraph("  ");
                doc.add(p);

                PdfPTable table = new PdfPTable(3);
                PdfPCell cel1 = new PdfPCell(new Paragraph("ID"));
                PdfPCell cel2 = new PdfPCell(new Paragraph("Nome"));
                PdfPCell cel3 = new PdfPCell(new Paragraph("Qnt. compras"));

                table.addCell(cel1);
                table.addCell(cel2);
                table.addCell(cel3);

                if (clientes != null && !clientes.isEmpty()) {

                    for (Cliente cliente : clientes) {
                        int qntCompra = compraModelo.obterComprasQuantidade(dataInicial.getTime(), dataFinal.getTime(), cliente.getId());
                        if (qntCompra != 0) {
                            cel1 = new PdfPCell(new Paragraph(cliente.getId() + ""));
                            cel2 = new PdfPCell(new Paragraph(cliente.getNome()));
                            cel3 = new PdfPCell(new Paragraph(qntCompra + ""));

                            table.addCell(cel1);
                            table.addCell(cel2);
                            table.addCell(cel3);
                        }
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
