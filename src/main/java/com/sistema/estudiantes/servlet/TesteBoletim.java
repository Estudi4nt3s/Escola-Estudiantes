package com.sistema.estudiantes.servlet;


import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.*;
import com.itextpdf.layout.borders.SolidBorder;

public class TesteBoletim {

    public static void main(String[] args) throws Exception {

        PdfWriter writer = new PdfWriter("boletim_modelo_ajustado.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.setMargins(20, 20, 20, 20);

        // ================= CABEÇALHO =================
        float[] colCab = {350, 200};
        Table cabecalho = new Table(colCab);
        cabecalho.setWidth(UnitValue.createPercentValue(100));

        cabecalho.addCell(criarCelula("ALUNO(A): Carlos Eduardo Domingues Bellomo"));
        cabecalho.addCell(criarCelula("ANO LETIVO: 2025"));

        cabecalho.addCell(criarCelula("TURMA: 1ª Série E Tech - Nº 5"));
        cabecalho.addCell(criarCelula("SITUAÇÃO FINAL: Aprovado"));

        cabecalho.addCell(criarCelula("UNIDADE: Escola Germinare"));
        cabecalho.addCell(criarCelula("EMISSÃO: 26/11/2025"));

        document.add(cabecalho);
        document.add(new Paragraph("\n"));

        // ================= TABELA PRINCIPAL =================
        float[] colunas = {300, 70, 70, 70, 120};
        Table tabela = new Table(colunas);
        tabela.setWidth(UnitValue.createPercentValue(100));

        tabela.addHeaderCell(criarHeader("Disciplinas"));
        tabela.addHeaderCell(criarHeader("1ºSem"));
        tabela.addHeaderCell(criarHeader("2ºSem"));
        tabela.addHeaderCell(criarHeader("MF"));
        tabela.addHeaderCell(criarHeader("Situação"));

// Linha 1
        tabela.addCell(criarCelula("Banco de dados"));
        tabela.addCell(criarNota("8.45"));
        tabela.addCell(criarNota("8.30"));
        tabela.addCell(criarNota("8.35"));
        tabela.addCell(criarNota("-"));

// Linha 2
        tabela.addCell(criarCelula("Programação Orientada a Objetos"));
        tabela.addCell(criarNotaVermelha("6.81"));
        tabela.addCell(criarNota("7.95"));
        tabela.addCell(criarNota("7.57"));
        tabela.addCell(criarNota("-"));

        document.add(tabela);

        document.close();
        System.out.println("Boletim ajustado gerado!");
    }

    private static Cell criarCelula(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setBorder(Border.NO_BORDER);
    }

    private static Cell criarHeader(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setBold().setFontSize(9))
                .setTextAlignment(TextAlignment.CENTER)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                .setBorder(new SolidBorder(1));
    }

    private static Cell criarNota(String texto) {
        return new Cell()
                .add(new Paragraph(texto).setFontSize(9))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1));
    }

    private static Cell criarNotaVermelha(String texto) {
        return new Cell()
                .add(new Paragraph(texto)
                        .setFontSize(9)
                        .setFontColor(ColorConstants.RED))
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(new SolidBorder(1));
    }
}