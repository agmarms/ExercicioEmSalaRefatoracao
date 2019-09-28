package br.com.diego.notafiscal;

import java.math.BigDecimal;

public class ISS implements IImposto{

	public BigDecimal valorImposto() {
		return BigDecimal.valueOf(0.1);
	}
}
