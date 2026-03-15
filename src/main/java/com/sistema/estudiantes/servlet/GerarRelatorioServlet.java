package com.sistema.estudiantes.servlet;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.sistema.estudiantes.dao.RelatorioDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/gerarRelatorio")
public class GerarRelatorioServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Boletim_Escolar.pdf\"");

        try {
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("RELATÓRIO DE DESEMPENHO INSTITUCIONAL - 2026").setBold().setFontSize(18));
            document.add(new Paragraph("Data de emissão: " + new java.util.Date().toString()));
            document.add(new Paragraph(" "));

            RelatorioDAO relDao = new RelatorioDAO();
            List<Map<String, String>> lista = relDao.buscarDadosRelatorio();

            String turmaAtual = "";
            Table table = null;

            for (Map<String, String> item : lista) {
                // Se a turma mudar, fecha a tabela anterior (se existir) e cria nova
                if (!item.get("turma").equals(turmaAtual)) {
                    if (table != null) {
                        document.add(table);
                    }
                    turmaAtual = item.get("turma");
                    document.add(new Paragraph("TURMA: " + turmaAtual).setBold().setFontSize(14));

                    table = new Table(UnitValue.createPercentArray(new float[]{3, 3, 2, 2}));
                    table.setWidth(UnitValue.createPercentValue(100));

                    // Cabeçalhos em negrito
                    table.addHeaderCell(new Cell().add(new Paragraph("Disciplina").setBold()));
                    table.addHeaderCell(new Cell().add(new Paragraph("Professor").setBold()));
                    table.addHeaderCell(new Cell().add(new Paragraph("Média").setBold()));
                    table.addHeaderCell(new Cell().add(new Paragraph("Alunos").setBold()));
                }

                // Adiciona dados (SEM negrito)
                table.addCell(new Cell().add(new Paragraph(item.get("materia"))));
                table.addCell(new Cell().add(new Paragraph(item.get("professor"))));
                table.addCell(new Cell().add(new Paragraph(item.get("media"))));
                table.addCell(new Cell().add(new Paragraph(item.get("qtd"))));
            }

            // Adiciona a última tabela que ficou aberta no loop
            if (table != null) {
                document.add(table);
            }

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Erro ao gerar PDF", e);
        }
    }
}