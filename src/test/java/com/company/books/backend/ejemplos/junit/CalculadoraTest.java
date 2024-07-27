package com.company.books.backend.ejemplos.junit;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CalculadoraTest {
	
	Calculadora calc;
	
	@BeforeAll
	public static void primero() {
		System.out.println("Primero (Antes de la prueba unitaria, unica vez)");
	}
	
	@AfterAll
	public static void ultimo() {
		System.out.println("Ultimo (Al final de la prueba unitaria, unica vez)");
	}
	
	@BeforeEach
	public void instanciaObjeto() {
		calc = new Calculadora();
		System.out.println("@BeforeEach(Antes del test unitario)");
	}

	@AfterEach
	public void despuesTest() {
		System.out.println("@AfterEach(Despues del test unitario)");
	}
	
	// @DisplayName es para crear una descripcion del test unitario
	// @Disabled se utiliza para la desabilitacion del test unitario
	@Test
	@DisplayName("prueba que ocupa assertEquals")
	@Disabled("esta prueba no se ejecuta")
	public void calculadoraAssertEqualTest() {
		
		assertEquals(2, calc.sumar(1, 1));
		assertEquals(3, calc.restar(4, 1));
		assertEquals(9, calc.multiplicar(3, 3));
		assertEquals(2, calc.dividir(4, 2));
		
		System.out.println("calculadoraAssertEqualTest");
	}
	
	@Test
	@DisplayName("prueba que ocupa assertTrue y assertFalse")
	public void calculadoraTrueFalse() {
		
		assertTrue(calc.sumar(1, 1) == 2);
		assertTrue(calc.restar(4, 1) == 3);
		assertFalse(calc.multiplicar(3, 3) == 8);
		assertFalse(calc.dividir(4, 2) == 1);
		
		System.out.println("calculadoraTrueFalse");
	}
}
