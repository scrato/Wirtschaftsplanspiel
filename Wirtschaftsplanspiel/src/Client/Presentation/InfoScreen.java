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
			super("Informationen über Business Basics");
			GridBagConstraints c = new GridBagConstraints();
			this.setSize(1000,700);
			this.setResizable(false);
			this.setVisible(true);
			this.setLocation(50, 0);
			Font bold = new Font("Verdana", Font.BOLD, 12);
			JTextArea text1 = new JTextArea("Diese Anwendung wurde im Rahmen einer Fallstudie von vier Wirtschaftsinformatikern (WWI10SWMB10) der DHBW Mannheim im Jahr 2012 entwickelt. \n"
					+ "\nVorbereitung: \n"
					+ "Jeder Anwender ( Spieler ) benötigt die aktuelle Version der Anwendung.\n"
					+ "Ein Spieler erzeugt einen Server und legt die zuspielende Anzahl an Perioden fest.\n"
					+ "Alle weiteren Spieler verbinden sich mit der IP Adresse des Spielers, der den Server gestartet hat.\n"
					+ " Haben sich alle Spieler erfolgreich verbunden, startet der Spielersteller das Spiel über Menü -> „Server“ - > „Spiel starten“ \n" 
					+ "Ab jetzt können sich keine weiteren Spieler verbinden.\n"
					+ "\n"
					+ "\nINFO \n"
					+ "Sie sind Mitglied der Unternehmensführung eines Unternehmens, das zusammen mit anderen Unternehmen auf einem homogenen Markt als Großhändler Fisch verkauft."
					+ "Als Startkapital stehen Ihnen 10 Millionen Euro zur Verfügung mit denen Sie in der ersten Periode sich ihr Umlaufs- und Anlagevermögen beschaffen können. Reicht das Ihnen nicht aus, so wird Ihnen die Möglichkeit geboten ein Darlehen aufzunehmen."
					+ "Da es sich bei Ihren Unternehmen um eine GmbH handelt müssen Sie beachten, dass als fixe Kosten periodisch 40.000€ kalkulatorische Unternehmerkosten anfallen. Des Weiteren geht man davon aus, dass bereits eine Halle angemietet worden ist in der ihre Produktion stattfindet und die fixe Kosten in Höhe von 30.000€ je Periode mit sich bringt."
					+ "Wollen Sie einem Mitarbeiter kündigen oder einstellen so fallen Leistungen in Höhe von 10.000€ beim Einstellen und 15.000€ Abfindung bei einer Kündigung an."
					+ "Da man davon ausgeht, dass ein Wertverfall der Maschinen vorliegt werden Sie bei einem Verkauf einer Maschine nur den um die Abschreibungen geminderten Wert für diese erwirtschaften."
			  		+ "\n\nSpielanleitung\n"
			  		+ "Jeder Spieler sollte am Anfang einer jeder Periode mit der Absatz- und Preisplanung beginnen, da sich alle weiteren Ansichten, insbesondere die für die benötigte Kapazität, auf die dort eingeben Werte beziehen."
					+ "Genauer gesagt bietet der Screen dem Spieler die Möglichkeit die geplante Verkaufsmenge, sowie den geplanten Verkaufspreis einzugeben. Des Weiteren ist es dem Spieler möglich unter „geplante Produktionsmenge“ die Palettenanzahl einzugeben die er in der laufenden Periode produzieren möchte. Die Werte sind keine verbindlichen Eingaben, sondern geben die bei der geplanten Produktionsmenge benötigten Ressourcen – sprich Rohfisch bzw. Verpackungsmaterial–  sowie auch fehlende Kapazitäten - Maschine, Mitarbeiter - an. Der eigentliche Einkauf der Werksstoffe findet im nächsten Screen statt auf den man durch Auswahl des „weiter“-Buttons oder im Menu unter „Werkstoffe einkaufen“ gelangt."
					+ "Hier können Sie die beiden für die Produktion benötigten Werkstoffe einkaufen. Er hat die Wahl entweder seine zu kaufende Menge selbst festzulegen oder durch drücken des Buttons „Alle benötigten Rohstoffe einkaufen“ die notwendigen Rohstoffe direkt durch das System einkaufen zu lassen."
					+ "Hilfe bekommt er durch eine Ausgabe in der angezeigt wird wie viel er bei der im ersten Screen festgelegten Produktionsmenge an Werkstoffe benötigt."
					+ "Weiterhin sieht der Spieler die bisher auf Lager liegenden Rohstoffe. In der ersten Periode wird dieser auf null sein, da man davon ausgeht, dass das Anlage und Umlaufvermögen erst eingekauft werden muss und in den darauf folgenden Perioden auf diesen Entscheidungen aufgebaut wird."
					+ "Der Lagerbestand kann entweder dadurch erhöht werden, dass nicht alle Produkte in einer Periode produziert oder nicht vollständig verkauft wurden erhöht werden."
					+ "Ist der Kauf getätigt gelangt man wiederum durch Auswahl des Buttons „weiter“ der auf jedem Screen verfügbar ist zur Maschinenverwaltung."
					+ "Wahlweise kann man hier Filetiermaschinen oder Verpackungsmaschinen kaufen. Auch hier zeigt das System an wie viele Maschinen vom jeweiligen Typen noch gebraucht werden. Dies hängt auch von der ausgewählten Kapazität der Maschine ab, die auswählen kann."
					+ "Kauft man eine Maschine dazu, so wird diese als Anlage aufgelistet. Anders herum möchte man eine verkaufen, wählt man eine aus seinem Sortiment aus und drückt den Button verkaufen."
					+ "Beim nächsten Untermenu handelt es sich um die Personalverwaltung. Diese ist ähnlich aufgebaut wie bei der Maschinenverwaltung. Wiederum hat man die Möglichkeit eine bestimmte Anzahl von Mitarbeitern eines bestimmten Typs einzustellen oder zu entlassen." 
					+ "Benötigt man einen Kredit so kann man über das Menu „Darlehen“ sich Geld von der Bank leihen. Zu beachten ist, dass immer nur ein Kredit aufgenommen werden kann und dieser nicht höher als 9 Mio. Euro sein kann."
					+ "Ist man nun soweit mit der Periodenplanung fertig kann man seine Produktions- und Verkaufsdaten über den Button „Periode abschließen“ versenden."
					+ "Haben alle Spieler dies getan wird der Screen „Berichtserstattung“ frei geschalten (nur in der ersten Periode nicht auswählbar) hat eine Übersicht über die Verkaufsergebnisse."
					+ "Möchte man weitere Informationen über die Marktlage haben, so kann man hier einen Einblick in die Ergebnisse der anderen Spieler erlangen.");
			text1.setLineWrap(true);
			text1.setEditable(false);
			add(text1);
		}
	
}
