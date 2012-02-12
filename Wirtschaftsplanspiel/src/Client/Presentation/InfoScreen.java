package Client.Presentation;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class InfoScreen extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		public InfoScreen(){
			super("Informationen �ber Business Basics");
			GridBagConstraints c = new GridBagConstraints();
			this.setSize(1000,700);
			this.setResizable(false);
			this.setVisible(true);
			this.setLocation(50, 0);
			Font bold = new Font("Verdana", Font.BOLD, 12);
			JTextArea text1 = new JTextArea("Diese Anwendung wurde im Rahmen einer Fallstudie von vier Wirtschaftsinformatikern (WWI10SWMB10) der DHBW Mannheim im Jahr 2012 entwickelt. \n"
					+ "\nVorbereitung: \n"
					+ "Jeder Anwender ( Spieler ) ben�tigt die aktuelle Version der Anwendung.\n"
					+ "Ein Spieler erzeugt einen Server und legt die zuspielende Anzahl an Perioden fest.\n"
					+ "Alle weiteren Spieler verbinden sich mit der IP Adresse des Spielers, der den Server gestartet hat.\n"
					+ " Haben sich alle Spieler erfolgreich verbunden, startet der Spielersteller das Spiel �ber Men� -> �Server� - > �Spiel starten� \n" 
					+ "Ab jetzt k�nnen sich keine weiteren Spieler verbinden.\n"
					+ "\n"
					+ "\nINFO \n"
					+ "Sie sind Mitglied der Unternehmensf�hrung eines Unternehmens, das zusammen mit anderen Unternehmen auf einem homogenen Markt als Gro�h�ndler Fisch verkauft."
					+ "Als Startkapital stehen Ihnen 10 Millionen Euro zur Verf�gung mit denen Sie in der ersten Periode sich ihr Umlaufs- und Anlageverm�gen beschaffen k�nnen. Reicht das Ihnen nicht aus, so wird Ihnen die M�glichkeit geboten ein Darlehen aufzunehmen."
					+ "Da es sich bei Ihren Unternehmen um eine GmbH handelt m�ssen Sie beachten, dass als fixe Kosten periodisch 40.000� kalkulatorische Unternehmerkosten anfallen. Des Weiteren geht man davon aus, dass bereits eine Halle angemietet worden ist in der ihre Produktion stattfindet und die fixe Kosten in H�he von 30.000� je Periode mit sich bringt."
					+ "Wollen Sie einem Mitarbeiter k�ndigen oder einstellen so fallen Leistungen in H�he von 10.000� beim Einstellen und 15.000� Abfindung bei einer K�ndigung an."
					+ "Da man davon ausgeht, dass ein Wertverfall der Maschinen vorliegt werden Sie bei einem Verkauf einer Maschine nur den um die Abschreibungen geminderten Wert f�r diese erwirtschaften."
			  		+ "\n\nSpielanleitung\n"
			  		+ "Jeder Spieler sollte am Anfang einer jeder Periode mit der Absatz- und Preisplanung beginnen, da sich alle weiteren Ansichten, insbesondere die f�r die ben�tigte Kapazit�t, auf die dort eingeben Werte beziehen."
					+ "Genauer gesagt bietet der Screen dem Spieler die M�glichkeit die geplante Verkaufsmenge, sowie den geplanten Verkaufspreis einzugeben. Des Weiteren ist es dem Spieler m�glich unter �geplante Produktionsmenge� die Palettenanzahl einzugeben die er in der laufenden Periode produzieren m�chte. Die Werte sind keine verbindlichen Eingaben, sondern geben die bei der geplanten Produktionsmenge ben�tigten Ressourcen � sprich Rohfisch bzw. Verpackungsmaterial�  sowie auch fehlende Kapazit�ten - Maschine, Mitarbeiter - an. Der eigentliche Einkauf der Werksstoffe findet im n�chsten Screen statt auf den man durch Auswahl des �weiter�-Buttons oder im Menu unter �Werkstoffe einkaufen� gelangt."
					+ "Hier k�nnen Sie die beiden f�r die Produktion ben�tigten Werkstoffe einkaufen. Er hat die Wahl entweder seine zu kaufende Menge selbst festzulegen oder durch dr�cken des Buttons �Alle ben�tigten Rohstoffe einkaufen� die notwendigen Rohstoffe direkt durch das System einkaufen zu lassen."
					+ "Hilfe bekommt er durch eine Ausgabe in der angezeigt wird wie viel er bei der im ersten Screen festgelegten Produktionsmenge an Werkstoffe ben�tigt."
					+ "Weiterhin sieht der Spieler die bisher auf Lager liegenden Rohstoffe. In der ersten Periode wird dieser auf null sein, da man davon ausgeht, dass das Anlage und Umlaufverm�gen erst eingekauft werden muss und in den darauf folgenden Perioden auf diesen Entscheidungen aufgebaut wird."
					+ "Der Lagerbestand kann entweder dadurch erh�ht werden, dass nicht alle Produkte in einer Periode produziert oder nicht vollst�ndig verkauft wurden erh�ht werden."
					+ "Ist der Kauf get�tigt gelangt man wiederum durch Auswahl des Buttons �weiter� der auf jedem Screen verf�gbar ist zur Maschinenverwaltung."
					+ "Wahlweise kann man hier Filetiermaschinen oder Verpackungsmaschinen kaufen. Auch hier zeigt das System an wie viele Maschinen vom jeweiligen Typen noch gebraucht werden. Dies h�ngt auch von der ausgew�hlten Kapazit�t der Maschine ab, die ausw�hlen kann."
					+ "Kauft man eine Maschine dazu, so wird diese als Anlage aufgelistet. Anders herum m�chte man eine verkaufen, w�hlt man eine aus seinem Sortiment aus und dr�ckt den Button verkaufen."
					+ "Beim n�chsten Untermenu handelt es sich um die Personalverwaltung. Diese ist �hnlich aufgebaut wie bei der Maschinenverwaltung. Wiederum hat man die M�glichkeit eine bestimmte Anzahl von Mitarbeitern eines bestimmten Typs einzustellen oder zu entlassen." 
					+ "Ben�tigt man einen Kredit so kann man �ber das Menu �Darlehen� sich Geld von der Bank leihen. Zu beachten ist, dass immer nur ein Kredit aufgenommen werden kann und dieser nicht h�her als 9 Mio. Euro sein kann."
					+ "Ist man nun soweit mit der Periodenplanung fertig kann man seine Produktions- und Verkaufsdaten �ber den Button �Periode abschlie�en� versenden."
					+ "Haben alle Spieler dies getan wird der Screen �Berichtserstattung� frei geschalten (nur in der ersten Periode nicht ausw�hlbar) hat eine �bersicht �ber die Verkaufsergebnisse."
					+ "M�chte man weitere Informationen �ber die Marktlage haben, so kann man hier einen Einblick in die Ergebnisse der anderen Spieler erlangen.");
			text1.setLineWrap(true);
			text1.setEditable(false);
			add(text1);
		}
	
}
