package com.sistema.estudiantes.servlet;

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
        @SuppressWarnings("unchecked")
        List<Disciplina> disciplinas = (List<Disciplina>) request.getSession().getAttribute("disciplinas");

        int iddisciplina;
        String situacao = "-";

        boolean aprovado = true;

        for (Nota nota : notas) {
            double media = (nota.getN1() + nota.getN2()) / 2;
            if (media < 7) {
                aprovado = false;
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

            // 🔥 AQUI VOCÊ COMEÇA A SUBSTITUIR DADOS

            String nomeAluno = user.getNome();
            String turma = serie.getSerie() + " " + serie.getLetra();
            String anoLetivo = String.valueOf(serie.getAno());

            // ================= CABEÇALHO =================
            float[] colCab = {350, 200};
            Table cabecalho = new Table(colCab);
            cabecalho.setWidth(UnitValue.createPercentValue(100));
            cabecalho.setBorder(new SolidBorder(1));

            cabecalho.addCell(criarCelulaCabecalho("ALUNO(A): " + nomeAluno));
            cabecalho.addCell(criarCelulaCabecalho("ANO LETIVO: " + anoLetivo));

            cabecalho.addCell(criarCelulaCabecalho("TURMA: " + turma));
            cabecalho.addCell(criarCelulaCabecalho("SITUAÇÃO FINAL: " + (aprovado ? "Aprovado" : "Reprovado")));

            cabecalho.addCell(criarCelulaCabecalho("UNIDADE: Escola Estudiantes"));
            cabecalho.addCell(criarCelulaCabecalho("EMISSÃO: " + LocalDate.now()));

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

                    double media = (notaEncontrada.getN1() + notaEncontrada.getN2()) / 2;

                    tabela.addCell(criarNota(String.format("%.2f",notaEncontrada.getN1())));
                    tabela.addCell(criarNota(String.format("%.2f",notaEncontrada.getN2())));
                    tabela.addCell(criarNota(String.format("%.2f",media)));

                    situacao = media >= 7 ? "Aprovado" : "Reprovado";
                    tabela.addCell(criarCelula(situacao));

                } else {

                    tabela.addCell(criarNota("-"));
                    tabela.addCell(criarNota("-"));
                    tabela.addCell(criarNota("-"));
                    tabela.addCell(criarCelula("-"));
                }
            }

            document.add(tabela);

            float[] colRodape = {300, 200};
            Table rodape = new Table(colRodape);
            rodape.setWidth(UnitValue.createPercentValue(100));

// ===== ASSINATURA (ESQUERDA) =====
            Cell assinatura = new Cell();
            assinatura.setBorder(Border.NO_BORDER);

            assinatura.add(new Paragraph("____________________________"));
            assinatura.add(new Paragraph("Assinatura do Diretor").setFontSize(9));

            rodape.addCell(assinatura);

// ===== IMAGEM (DIREITA) =====
            String caminhoImagem = getServletContext().getRealPath("/utils/logo.png");

            ImageData imageData = ImageDataFactory.create(caminhoImagem);
            Image imagem = new Image(imageData);

            imagem.setWidth(120);
            imagem.setHorizontalAlignment(HorizontalAlignment.RIGHT);

            Cell imagemCell = new Cell();
            imagemCell.setBorder(Border.NO_BORDER);
            imagemCell.add(imagem);

            rodape.addCell(imagemCell);

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
}