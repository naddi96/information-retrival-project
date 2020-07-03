import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StopWords {

    public List<String> stopwords;


    public StopWords() throws IOException {
        String eng="a\nabout\nabove\nafter\nagain\nagainst\nall\nam\nan\nand\nany\nare\naren\nt\nas\nat\nbe\nbecause\nbeen\nbefore\nbeing\nbelow\nbetween\nboth\nbut\nby\ncan\ncannot\ncould\ncouldn\ndid\ndidn\ndo\ndoes\ndoesn\ndoing\ndon\ndown\nduring\neach\nfew\nfor\nfrom\nfurther\nhad\nhadn\nhas\nhasn\nhave\nhaven\nhaving\nhe\nhe\nhe\nll\nhe\nher\nhere\nhere\nhers\nherself\nhim\nhimself\nhis\nhow\nhow's\ni\ni\nd\ni\nm\nve\nif\nin\ninto\nis\nisn\nit\nit\nits\nitself\nlet\nme\nmore\nmost\nmustn\nmy\nmyself\nno\nnor\nnot\nof\noff\non\nonce\nonly\nor\nother\nought\nour\nours\nourselves\nout\nover\nown\nsame\nshan\nshe\nshe\nd\nshe\nshe\nshould\nshouldn\nso\nsome\nsuch\nthan\nthat\nthat\nthe\ntheir\ntheirs\nthem\nthemselves\nthen\nthere\nthese\nthey\nthis\nthose\nthrough\nto\ntoo\nunder\nuntil\nup\nvery\nwas\nwasn\nwe\nwe\nre\nwere\nweren\nwhat\nwhat\nwhen\nwhen\nwhere\nwhere\nwhich\nwhile\nwho\nwho\nwhom\nwhy\nwhy\nwith\nwon\nwould\nwouldn\nyou\nyour\nyours\nyourself\nyourselves";
        String ita="a\nadesso\nai\nal\nalla\nallo\nallora\naltre\naltri\naltro\nanche\nancora\navere\naveva\navevano\nben\nbuono\nche\nchi\ncinque\ncomprare\ncon\nconsecutivi\nconsecutivo\ncosa\ncui\nda\ndel\ndella\ndello\ndentro\ndeve\ndevo\ndi\ndoppio\ndue\ne\necco\nfare\nfine\nfino\nfra\ngente\ngiu\nha\nhai\nhanno\nho\nil\nindietro\ninvece\nio\nla\nlavoro\nle\nlei\nlo\nloro\nlui\nlungo\nma\nme\nmeglio\nmolta\nmolti\nmolto\nnei\nnella\nno\nnoi\nnome\nnostro\nnove\nnuovi\nnuovo\no\noltre\nora\notto\npeggio\npero\npersone\npiu\npoco\nprimo\npromesso\nqua\nquarto\nquasi\nquattro\nquello\nquesto\nqui\nquindi\nquinto\nrispetto\nsara\nsecondo\nsei\nsembra\nsembrava\nsenza\nsette\nsia\nsiamo\nsiete\nsolo\nsono\nsopra\nsoprattutto\nsotto\nstati\nstato\nstesso\nsu\nsubito\nsul\nsulla\ntanto\nte\ntempo\nterzo\ntra\ntre\ntriplo\nultimo\nun\nuna\nuno\nva\nvai\nvoi\nvolte\nvostro\nq\nw\ne\nr\nt\ny\nu\ni\np\na\ns\nd\nf\ng\nh\nj\nk\nl\nz\nx\nc\nb\nn\nv\nm\n";
        ArrayList aList= new ArrayList(Arrays.asList(eng.split("\n")));
        this.stopwords = aList;
        this.stopwords.addAll(new ArrayList(Arrays.asList(ita.split("\n"))));
    }

    public String removeAll(String data) {
        ArrayList<String> allWords = Stream.of(data.split(" "))
                .collect(Collectors.toCollection(ArrayList<String>::new));
        allWords.removeAll(this.stopwords);
        return allWords.stream().collect(Collectors.joining(" "));
    }
}
