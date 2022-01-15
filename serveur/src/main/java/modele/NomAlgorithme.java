package modele;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "algo")
public interface NomAlgorithme {

	public static NomAlgorithme fromString(String titre) {
		return new ImplemNomAlgorithme(titre);
	}

	String getNom();
}
