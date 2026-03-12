package com.sistema.estudiantes.servlet;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.borders.*;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;

import com.sistema.estudiantes.model.*;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/gerarBoletim")
public class GerarBoletimServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario user = (Usuario)request.getSession().getAttribute("usuario");
        Turma serie = (Turma) request.getSession().getAttribute("turma");
        Aluno aluno = (Aluno) request.getSession().getAttribute("aluno");
        @SuppressWarnings("unchecked")
        List<Nota> notas = (List<Nota>) request.getSession().getAttribute("notas");
        System.out.println(notas.getLast().getN2());
        @SuppressWarnings("unchecked")
        List<Disciplina> disciplinas = (List<Disciplina>) request.getSession().getAttribute("disciplinas");

        String situacao;

        LocalDate data = LocalDate.now();
        String aprovado = "Aprovado";

        for (Nota nota : notas) {
            Double media = (nota.getN1() == null || nota.getN2() == null)?null:(nota.getN1() + nota.getN2())/ 2;
            if (media == null) {
                aprovado = "Em andamento";
                break;
            }

            if (media < 7){
                aprovado = "Reprovado";
                break;
            }
        }


        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boletim.pdf");

        try {

            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.setMargins(20, 20, 20, 20);

            // ================= TOPO COM LOGO =================
            float[] colTopo = {120, 500};
            Table topo = new Table(colTopo);
            topo.setWidth(UnitValue.createPercentValue(100));
            topo.setBorder(Border.NO_BORDER);

// LOGO
            String caminhoLogo = getServletContext().getRealPath("/utils/logo.png");

            ImageData logoData = ImageDataFactory.create(caminhoLogo);
            Image logo = new Image(logoData);
            logo.setWidth(100);
            logo.setMarginTop(18);

            Cell logoCell = new Cell();
            logoCell.setBorder(Border.NO_BORDER);
            logoCell.add(logo);
            topo.addCell(logoCell);

// TÍTULO
            Paragraph titulo = new Paragraph("Escola Estudiantes")
                    .setFontSize(18)
                    .setBold()
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);

            Cell tituloCell = new Cell();
            tituloCell.setBorder(Border.NO_BORDER);
            tituloCell.setVerticalAlignment(VerticalAlignment.MIDDLE);
            tituloCell.add(titulo);

            topo.addCell(tituloCell);

            document.add(topo);
            document.add(new Paragraph("\n"));

            String nomeAluno = aluno.getNome();
            String turma = serie.getNome();
            String anoLetivo = String.valueOf(serie.getAno());

            // ================= CABEÇALHO =================
            float[] colCab = {350, 200};
            Table cabecalho = new Table(colCab);
            cabecalho.setWidth(UnitValue.createPercentValue(100));
            cabecalho.setBorder(new SolidBorder(1));

            cabecalho.addCell(criarCelulaCabecalho("ALUNO(A): " + nomeAluno));
            cabecalho.addCell(criarCelulaCabecalho("ANO LETIVO: " + anoLetivo));

            cabecalho.addCell(criarCelulaCabecalho("TURMA: " + turma));
            cabecalho.addCell(criarCelulaCabecalho("SITUAÇÃO FINAL: " + (aprovado.equals("Aprovado") ? criarSituacao("Aprovado", ColorConstants.GREEN) : aprovado.equals("Reprovado")?criarSituacao("Reprovado", ColorConstants.RED):"Em andamento")));

            cabecalho.addCell(criarCelulaCabecalho("UNIDADE: Escola Estudiantes"));
            cabecalho.addCell(criarCelulaCabecalho("EMISSÃO: " + String.format("%02d", data.getDayOfMonth()) + "/" + String.format("%02d", data.getMonthValue()) + "/" + data.getYear()));

            document.add(cabecalho);
            document.add(new Paragraph("\n"));

            // ================= TABELA =================
            float[] colunas = {300, 70, 70, 70, 120};
            Table tabela = new Table(colunas);
            tabela.setWidth(UnitValue.createPercentValue(100));

            tabela.addHeaderCell(criarHeader("Disciplinas"));
            tabela.addHeaderCell(criarHeader("1ºSem"));
            tabela.addHeaderCell(criarHeader("2ºSem"));
            tabela.addHeaderCell(criarHeader("MF"));
            tabela.addHeaderCell(criarHeader("Situação"));
            for (Disciplina disciplina : disciplinas) {

                tabela.addCell(criarCelula(disciplina.getNome().toUpperCase().charAt(0) + disciplina.getNome().toLowerCase().substring(1,disciplina.getNome().length())));

                Nota notaEncontrada = null;

                for (Nota nota : notas) {
                    if (nota.getIdDisciplina().getId() == disciplina.getId()) {
                        notaEncontrada = nota;
                        break;
                    }
                }

                if (notaEncontrada != null) {
                    Double n1 = notaEncontrada.getN1();
                    Double n2 = notaEncontrada.getN2();

                    Double media = null;

                    if (n1 != null && n2 != null) {
                        media = (notaEncontrada.getN1() + notaEncontrada.getN2()) / 2;
                    }

                    tabela.addCell(criarNota(n1 == null ? "-" : String.format("%.2f", n1)));
                    tabela.addCell(criarNota(n2 == null ? "-" : String.format("%.2f", n2)));
                    tabela.addCell(criarNota((n1 == null || n2 == null) ? "-" : String.format("%.2f", media)));

                    situacao = (n1 == null || n2 == null)?"-" : media >= 7 ? "Aprovado" : "Reprovado";
                    tabela.addCell(criarCelula(situacao).setTextAlignment(TextAlignment.CENTER));

                } else {

                    tabela.addCell(criarNota("-"));
                    tabela.addCell(criarNota("-"));
                    tabela.addCell(criarNota("-"));
                    tabela.addCell(criarCelula("-").setTextAlignment(TextAlignment.CENTER));
                }
            }

            document.add(tabela);

            float[] colRodape = {300};
            Table rodape = new Table(colRodape);
            rodape.setWidth(UnitValue.createPercentValue(100));

            // ===== ASSINATURA (ESQUERDA) =====
            // ===== ASSINATURA (ESQUERDA) =====
            Cell assinatura = new Cell();
            assinatura.setBorder(Border.NO_BORDER);
            assinatura.setTextAlignment(TextAlignment.LEFT);

            String caminhoImagem = getServletContext().getRealPath("/utils/assinatura.png");

            ImageData imageData = ImageDataFactory.create(caminhoImagem);
            Image imagem = new Image(imageData);

            imagem.setWidth(120);
            imagem.setHorizontalAlignment(HorizontalAlignment.LEFT);

            assinatura.add(imagem);

            Paragraph rg = new Paragraph("RG: 400.289.226-76")
                    .setFontSize(6)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setWidth(113); // mesma largura da imagem

            assinatura.add(rg);

            rodape.addCell(assinatura);
// espaço antes do rodapé
            document.add(new Paragraph("\n\n"));

            document.add(rodape);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== MÉTODOS AUXILIARES =====

    private Cell criarCelulaCabecalho(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setBorder(Border.NO_BORDER);
    }

    private Cell criarHeader(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setBold().setFontSize(9))
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(new SolidBorder(1));
    }

    private Cell criarCelula(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setBorder(new SolidBorder(1));
    }

    private Cell criarNota(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1));
    }

    private Cell criarSituacao(String texto, Color cor) {
        return new Cell()
                .add(new Paragraph(texto)
                        .setFontSize(9)
                        .setFontColor(cor))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1));
    }
}