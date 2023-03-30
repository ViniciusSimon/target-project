package br.com.targetsistemas;

import br.com.targetsistemas.model.Dados;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.*;

public class Main {
    public static void main(String[] args) throws IOException {

        chamarMenu();

    }

    private static void buscaValorFibonacci() {

        int valorBusca = Integer.parseInt(JOptionPane.showInputDialog("Informe o valor a ser procurado na sequência de Fibonacci"));

        ArrayList<Integer> sequencia = new ArrayList<>();
        sequencia.add(0);
        sequencia.add(1);

        for (int i = 1; sequencia.get(i) < valorBusca; i++) {
            int primeiroValor = sequencia.get(i-1);
            int segundoValor = sequencia.get(i);
            sequencia.add(primeiroValor+segundoValor);
        }

        if (sequencia.contains(valorBusca)) {
            JOptionPane.showMessageDialog(null, "O valor informado ESTÁ presente na sequência.");
        } else {
            JOptionPane.showMessageDialog(null, "O valor informado NÃO ESTÁ presente na sequência.");
        }
    }

    private static int diasSuperioresAMediaFaturamento() throws IOException {

        List<Dados> dados = retornaDadosJson();

        int quantidadeDias = dados.size();
        double soma = 0;
        for (Dados dado : dados) {
            if (dado.getValor() > 0) {
                soma += dado.getValor();
            }
        }

        double media = soma/quantidadeDias;
        int totalDiasSuperioresAMedia = 0;
        for (Dados dado : dados) {
            if (dado.getValor() > media) {
                totalDiasSuperioresAMedia++;
            }
        }

        return totalDiasSuperioresAMedia;

    }

    private static List menorFaturamentoNoMes() throws IOException {

        List<Dados> dados = retornaDadosJson();

        double menorValor = MAX_VALUE;
        int diaMenorValor = 0;
        for (Dados dado : dados) {
            if (dado.getValor() > 0) {
                if (dado.getValor() < menorValor) {
                    menorValor = dado.getValor();
                    diaMenorValor = dado.getDia();
                }
            }
        }

        List valores = new ArrayList<>();
        valores.add(menorValor);
        valores.add(diaMenorValor);

        return valores;
    }

    private static List maiorFaturamentoNoMes() throws IOException {

        List<Dados> dados = retornaDadosJson();

        double maiorValor = MIN_VALUE;
        int diaMaiorValor = 0;
        for (Dados dado : dados) {
            if (dado.getValor() > 0) {
                if (dado.getValor() > maiorValor) {
                    maiorValor = dado.getValor();
                    diaMaiorValor = dado.getDia();
                }
            }
        }

        List valores = new ArrayList();
        valores.add(maiorValor);
        valores.add(diaMaiorValor);

        return valores;
    }

    private static List<Dados> retornaDadosJson() throws IOException {
        String json = String.join(" ",
                Files.readAllLines(
                        Paths.get("./json/dados.json"),
                        StandardCharsets.UTF_8)
        );

        Type type = new TypeToken<List<Dados>>(){}.getType();

        return new Gson().fromJson(json, type);
    }

    private static void calculaPercentualPorEstado() {

        Object[] estados = {"SP", "RJ", "MG", "ES", "Outros"};
        Object estado = JOptionPane.showInputDialog(
                null,
                "Escolha o estado:",
                "Porcentagem por estado",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                estados,
                estados[0]
        );

        Map<String, Double> estadosFaturamento = new HashMap<String, Double>();

        estadosFaturamento.put("SP", 67836.43);
        estadosFaturamento.put("RJ", 36678.66);
        estadosFaturamento.put("MG", 29229.88);
        estadosFaturamento.put("ES", 27165.48);
        estadosFaturamento.put("Outros", 19849.53);

        // Total deve ser igual a 180759.98
        double total = 0;
        for(Map.Entry<String,Double> pair : estadosFaturamento.entrySet()){
            total += pair.getValue();
        }

        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        String porcentagem;
        porcentagem = df.format((estadosFaturamento.get(estado) * 100)/total);

        JOptionPane.showMessageDialog(null, "O estado de " + estado + " constitui " + porcentagem + "% do faturamento mensal da empresa");
    }

    private static void inverterString() {
        String frase = JOptionPane.showInputDialog("Informe a String que deverá ser invertida: ");
        String fraseInvertida = "";

        for (int i = frase.length()-1; i >= 0; i--) {
            fraseInvertida += frase.charAt(i);
        }

        JOptionPane.showMessageDialog(null, fraseInvertida);
    }

    public static void chamarMenu() throws IOException {

        Object[] opcoes = {
                "Sequência de Fibonacci",
                "Busca em JSON pelo faturamento mensal",
                "Percentual de representação por estado",
                "Inverter String"
        };
        Object opcao = JOptionPane.showInputDialog(
                null,
                "Escolha a função que deseja testar",
                "Menu",
                JOptionPane.INFORMATION_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        // Exercício 2-Sequência de Fibonacci
        if (opcao == "Sequência de Fibonacci") {
            buscaValorFibonacci();
        }

        // Exercício 3-Buscar valores em JSON
        if (opcao == "Busca em JSON pelo faturamento mensal") {
            int diasSuperiores = diasSuperioresAMediaFaturamento();
            List maiorFaturamento;
            List menorFaturamento;

            maiorFaturamento = maiorFaturamentoNoMes();
            menorFaturamento = menorFaturamentoNoMes();

            JOptionPane.showMessageDialog(
                    null,
                    diasSuperiores + " dias tiveram o faturamento superior à média mensal."
            );
            JOptionPane.showMessageDialog(
                    null,
                    "O maior faturamento foi de R$" + maiorFaturamento.get(0) + " no dia " + maiorFaturamento.get(1)
            );
            JOptionPane.showMessageDialog(
                    null,
                    "O menor faturamento foi de R$" + menorFaturamento.get(0) + " no dia " + menorFaturamento.get(1)
            );
        }

        // Exercício 4-Percentual de representação por estado
        if (opcao == "Percentual de representação por estado") {
            calculaPercentualPorEstado();
        }

        // Exercício 5-Inverter String
        if (opcao == "Inverter String") {
            inverterString();
        }

    }

}