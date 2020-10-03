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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import modelo.produtos.Produto;
import modelo.produtos.ProdutoModelo;

public class RelatorioProdutosServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Document doc = new Document();
        String arquivoPdf = "relatorio.pdf";
        String mensagem = "relatorioFalha";

        ProdutoModelo produtoModelo = new ProdutoModelo();

        try {
            List<Produto> produtos = produtoModelo.obterProdSemEstoque();
            PdfWriter.getInstance(doc, new FileOutputStream(arquivoPdf));
            doc.open();

            Paragraph p = new Paragraph("Produtos sem estoque");
            p.setAlignment(1);
            doc.add(p);
            p = new Paragraph("  ");
            doc.add(p);

            PdfPTable table = new PdfPTable(3);
            PdfPCell cel1 = new PdfPCell(new Paragraph("ID"));
            PdfPCell cel2 = new PdfPCell(new Paragraph("Descrição"));
            PdfPCell cel3 = new PdfPCell(new Paragraph("Preço"));

            table.addCell(cel1);
            table.addCell(cel2);
            table.addCell(cel3);

            if (produtos != null && !produtos.isEmpty()) {
                for (Produto produto : produtos) {
                    cel1 = new PdfPCell(new Paragraph(produto.getId() + ""));
                    cel2 = new PdfPCell(new Paragraph(produto.getDescricao()));
                    cel3 = new PdfPCell(new Paragraph("R$ " + produto.getPreco() + ""));

                    table.addCell(cel1);
                    table.addCell(cel2);
                    table.addCell(cel3);
                }
            }
            doc.add(table);
            doc.close();
            Desktop.getDesktop().open(new File(arquivoPdf));

            mensagem = "relatorioSucesso";
        } catch (DocumentException ex) {
            Logger.getLogger(RelatorioProdutosServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RelatorioProdutosServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("mensagem", mensagem);
        RequestDispatcher dispatcher = request.getRequestDispatcher("RelatoriosAdmServlet");
        dispatcher.forward(request, response);
    }
}
