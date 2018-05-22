import java.net.*;
import java.io.*;
import java.util.*;

public class Fila {

	int inicio;
	int fim;
	int tamanho;
	int qtdeElementos;
	int f[];

	public Fila() {
		inicio = fim = -1;
		tamanho = 200;
		f = new int[tamanho];
		qtdeElementos = 0;
	}

	public boolean estaCheia() {
		if (qtdeElementos == tamanho - 1) {
			return true;
		}
		return false;
	}

	public boolean estaVazia() {
		if (qtdeElementos == 0) {
			return true;
		}
		return false;
	}

	public void add(int e) {
		if (!estaCheia()) {
			if (inicio == -1) {
				inicio = 0;
			}
			fim++;
			f[fim] = e;
			qtdeElementos++;
		}
	}

	public void rem() {
		if (!estaVazia()) {
			inicio++;
			qtdeElementos--;
		}
	}

	public void printFila() {
		int i;
		if (!estaVazia()) {
			for (i = inicio; i < fim; i++) {
				System.out.println("Elemento " + i + ": " + f[i]);
			}
		}
	}
}