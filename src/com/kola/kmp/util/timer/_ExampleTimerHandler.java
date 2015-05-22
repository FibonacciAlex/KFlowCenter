package com.kola.kmp.util.timer;

import java.util.List;
import java.util.Set;

import org.jdom.Element;

public class _ExampleTimerHandler implements KGameTimerHandler {

	@Override
	public void init(KGameTimer timer, List<Element> taskElementsConfigInXml) {
		System.out.println(_ExampleTimerHandler.class.getName() + " init( "
				+ timer + "," + taskElementsConfigInXml + ") be called.");
		
		
		
	}

	@Override
	public void timerStopped(KGameTimer timer, Set<KGameTimeSignal> canceled) {
		for (KGameTimeSignal kGameTimeSignal : canceled) {
			System.out.println("canceled "+kGameTimeSignal);
		}
	}

}
