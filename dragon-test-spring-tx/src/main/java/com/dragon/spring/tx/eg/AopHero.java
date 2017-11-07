package com.dragon.spring.tx.eg;

public class AopHero implements Hero {
	
	private Hero littleFishHero;
	private Hero sliHero;

	@Override
	public String getName() {
		return "超级英雄";
	}

	@Override
	public void skill() {
		littleFishHero.skill();
		sliHero.skill();
		System.out.println("小鱼跟沉默配合double kill！");
	}

	@Override
	public int getMana() {
		return 0;
	}

	public Hero getLittleFishHero() {
		return littleFishHero;
	}

	public void setLittleFishHero(Hero littleFishHero) {
		this.littleFishHero = littleFishHero;
	}

	public Hero getSliHero() {
		return sliHero;
	}

	public void setSliHero(Hero sliHero) {
		this.sliHero = sliHero;
	}
	
}