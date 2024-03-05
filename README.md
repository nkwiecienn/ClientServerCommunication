Poniższy program służy do rozgrywki w pokera 5-kartowego dobieranego.

Zaimplementowane przeze mnie zasady gry:
- na początku gry każdy z graczy dostaje 5 kart;
- następnie rozpoczyna się pierwsza faza stawiania zakładów;
- nie ma ustalonego pierwszego zakładu;
- faza kończy się kiedy wszyscy gracze mają wyłożoną równą kwotę;
- następnie każdy z graczy może wymienić do 4 kart;
- rozpoczyna się druga faza stawiania zakładów;
- remisy są rozstrzygane, używając najlepszej karty dla danej ręki np.: dla dwóch par wygra para z lepszą kartą, dla full house lepsza trójka;
- jeżeli dalej nie można rozstrzygnąć remisu, stawka jest dzielona pomiędzy nimi.

Komunikacja z serverem:
- server przed każdym ruchem informuje o możliwych czynnościach;
- grę rozpoczyna się komunikatem "new"/"existing" w celu odpowiednio stworzenia/dołączenia do gry;
- następnie należy podać liczbę graczy (od 2 do 4) lub też ID gry (ID gry to GameN gdzie N jest numerem hosta danej gry);
- server dalej informuje o przebiegu gry oraz możliwych komendach;
- ważne jest, aby pamiętać, że podczas fazy stawiania zakładów należy podać stawkę DO której podbijamy.

Obsługa błędów:
- w przypadku podania błędnej wartości server informuje o tym i pozwala wpisać daną wartość jeszcze raz (do skutku).

Sposób uruchomienia programu:
- na początku należy uruchomić server, kompilując plik Server.java. Ważne jest, aby zrobić to przed uruchomieniem klienta;
- następnie mogą dołączyć do niego gracze, kompilując plik Client.java.