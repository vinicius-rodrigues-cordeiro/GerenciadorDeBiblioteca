package com.biblioteca;

import com.biblioteca.model.Usuario;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var sc = new Scanner(System.in);

        System.out.println("Digite seu nome:");
        String nome1 = sc.nextLine();

        System.out.println("Digite seu e-mail:");
        String email1 = sc.nextLine();

        System.out.println("Digite sua senha:");
        String senha1 = sc.nextLine();

        System.out.println("Digite o tipo de usuário: (Estudante, Bibliotecario, Administrador)");
        String tipo1 = sc.nextLine();

        System.out.println("Digite a data de Cadastro:");
        String data1 = sc.nextLine();

        var user = new Usuario("Vinicius","vrc_crv@outlook.com.br","@Kinha67");

        System.out.println(user.getNome());
        System.out.println(user.getEmail());
        System.out.println(user.getSenha());

    }
}