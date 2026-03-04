package com.sistema.estudiantes.servlet;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.borders.*;

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

        for (Disciplina disciplina : disciplinas) {
            for (Nota nota : notas) {
                if(nota.getIdDisciplina().getId() == disciplina.getId()){
                    iddisciplina = disciplina.getId();

                    situacao = ((nota.getN1() + nota.getN2())/2 >= 7)?"Aprovado":"Reprovado";
                    aprovado = (nota.getN1() + nota.getN2())/2 >= 7;
                }
                else{

                }
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
            cabecalho.addCell(criarCelulaCabecalho("SITUAÇÃO FINAL: " + (aprovado?"Aprovado":"Reprovado")));

            cabecalho.addCell(criarCelulaCabecalho("UNIDADE: Escola Germinare"));
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
                tabela.addCell(criarCelula(disciplina.getNome()));
                for (Nota nota : notas) {
                    if(nota.getIdDisciplina().getId() == disciplina.getId()){
                        iddisciplina = disciplina.getId();
                        tabela.addCell(criarCelula(String.valueOf(nota.getN1())));
                        tabela.addCell(criarCelula(String.valueOf(nota.getN2())));
                        tabela.addCell(criarCelula(String.valueOf((nota.getN1() + nota.getN2())/2)));
                        situacao = (nota.getN1() + nota.getN2()/2 >= 7)?"Aprovado":"Reprovado";
                        tabela.addCell(criarCelula(situacao));
                    }
                    else{
                        tabela.addCell(criarCelula(disciplina.getNome()));
                        tabela.addCell(criarCelula("-"));
                        tabela.addCell(criarCelula("-"));
                        tabela.addCell(criarCelula("-"));
                    }
                }
            }

            document.add(tabela);

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