

import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

public class TestReconhecedor  {
	
	@Test
	public void testa_codigo_novo() throws IOException {
		java.io.Reader input = new FileReader("src/main/resources/et.in");
		Main.start(input);
	}
	
}
