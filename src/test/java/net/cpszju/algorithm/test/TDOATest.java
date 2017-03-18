package net.cpszju.algorithm.test;

import net.cpszju.localization.algorithm.TDOA;

import org.junit.Test;

public class TDOATest {
	@Test
	public void test(){
		String path1 = "e:/tdoadata/sigB.wav";
		String path2 = "e:/tdoadata/sigA.wav";
		String path3 = "e:/tdoadata/sigC.wav";
		String path4 = "e:/tdoadata/sigD.wav";
		String[] path = new String[]{path1,path2,path3,path4};
		
		int[][] beacon = new int[][]{{4,0},{0,0},{4,4},{0,4}}; 
		
		TDOA tdoa = new TDOA(path,beacon);
		float[] result = tdoa.getResult();
		System.out.println(result[0]+"!"+result[1]);
	}
}
