package com.dragon.basic;

public class CatchException {

	public static int aMethod(int i) throws Exception {
		try {
			return i / 10;
		} catch(Exception ex) {
			throw new Exception("exception in a Method");
		} finally {
			System.out.println("finally");
		}
	}
	
	public static void main(String[] args) {
		try {
			aMethod(0);
		} catch (Exception e) {
			System.out.println("exception in main");
		}
		System.out.println("finished");
	}
	
}

/*finally
finished*/