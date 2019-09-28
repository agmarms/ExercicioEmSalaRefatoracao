package br.com.diego.notafiscal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NotaFiscalController {
	private static final String LISTANOTASFISCAIS = "listanotasfiscais";
	private static final String NOTASFISCAIS = "notasfiscais";
	@Autowired
	private NotaFiscalRepository rp;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping(LISTANOTASFISCAIS)
	public String listaNotasFiscais(Model model) {
		Iterable<NotaFiscal> nf = rp.findAll();
		model.addAttribute(NOTASFISCAIS, nf);
		return LISTANOTASFISCAIS;
	}

	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam("nome") String nome, @RequestParam("valor") Double valor,
			@RequestParam("imposto") String imposto, Model model) {
		IImposto impostoSelecionado;
		if (imposto.toUpperCase().trim().compareTo("ICMS") == 0)
			impostoSelecionado = new ICMS();
		else
			impostoSelecionado = new ISS();

		NotaFiscal nf = new NotaFiscal(nome, impostoSelecionado.valorImposto().doubleValue(), valor);
		rp.save(nf);
		Iterable<NotaFiscal> listaNotaFiscal = rp.findAll();
		model.addAttribute(NOTASFISCAIS, listaNotaFiscal);
		return LISTANOTASFISCAIS;	
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletar(@RequestParam("id") Integer id, Model model) {

		NotaFiscal nf = rp.findOne(id.longValue());
		if (nf != null) {
			rp.delete(nf);
		}
		Iterable<NotaFiscal> listaNotaFiscal = rp.findAll();
		model.addAttribute(NOTASFISCAIS, listaNotaFiscal);
		return LISTANOTASFISCAIS;	
	}
	
}