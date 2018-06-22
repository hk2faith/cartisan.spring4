package org.openkoreantext.processor;

import java.util.List;

import org.junit.Test;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;

import scala.collection.Seq;

/**
 * @author Hyungkook Kim
 *
 */
public class OpenKoreanTextProcessorTest {
	@Test
	public static void test(String[] args) {
	    String text = "한국어를 처리하는 test 예시입니닼ㅋㅋㅋㅋㅋ #한국어,#test";

	    // Normalize
	    CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);
	    System.out.println("normalized : " + normalized);
	    // 한국어를 처리하는 예시입니다ㅋㅋ #한국어

	    // Tokenize
	    Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
	    System.out.println("tokens : " + tokens);
	    
	    System.out.println("tokensToJavaStringList : " + OpenKoreanTextProcessorJava.tokensToJavaStringList(tokens));
	    // [한국어, 를, 처리, 하는, 예시, 입니, 다, ㅋㅋ, #한국어]
	    
	    List<KoreanTokenJava> tokensToJavaKoreanTokenList = OpenKoreanTextProcessorJava.tokensToJavaKoreanTokenList(tokens);
	    System.out.println("tokensToJavaKoreanTokenList : " + tokensToJavaKoreanTokenList);
	    // [한국어(Noun: 0, 3), 를(Josa: 3, 1), 처리(Noun: 5, 2), 하는(Verb(하다): 7, 2), 예시(Noun: 10, 2),
	    // 입니다(Adjective(이다): 12, 3), ㅋㅋㅋ(KoreanParticle: 15, 3), #한국어(Hashtag: 19, 4)]
	    tokensToJavaKoreanTokenList.stream()
	    	.filter(t -> KoreanPosJava.Noun.equals(t.getPos()))
	    	.forEach(
	    		token -> {
	    			System.out.println(token);
	    		}
	    	);
	    // Phrase extraction
	    List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);
	    System.out.println("phrases : " + phrases);
	    // [한국어(Noun: 0, 3), 처리(Noun: 5, 2), 처리하는 예시(Noun: 5, 7), 예시(Noun: 10, 2), #한국어(Hashtag: 18, 4)]
	  }
}
