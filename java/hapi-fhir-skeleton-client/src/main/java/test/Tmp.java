package test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.PerformanceOptionsEnum;

public class Tmp {
	private static final org.slf4j.Logger ourLog = org.slf4j.LoggerFactory.getLogger(Tmp.class);

	public static void main(String[] args) {
		
		// 1.6 - no defer - Took 6700 ms - 6.7ms / pass
		// 1.6 - no defer - Took 6127 ms - 6.127ms / pass
		
		// 1.6 - defer - Took 2523 ms - 2.523ms / pass
		// 1.6 - defer - Took 2328 ms - 2.328ms / pass
		
		// 1.5 - Took 77391 ms - 77.391ms / pass
		// 1.4 - Took 12645 ms - 12.645ms / pass
		// 1.3 - 
		
		// 1.4 - 560ms
		// 1.5 - 800ms
		// 1.6 - 340ms
		// 1.6 (deferred scanning) - 240ms
		
		int passes = 1;
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < passes; i++) {
			FhirContext ctx = FhirContext.forDstu2();
			ctx.setPerformanceOptions(PerformanceOptionsEnum.DEFERRED_MODEL_SCANNING);
			ctx.getResourceDefinition("Observation");
		}
		long end = System.currentTimeMillis();
		
		long delay = end-start;
		float per = (float)delay / (float)passes;
		
		ourLog.info("Took {} ms - {}ms / pass", delay, per);
	}

}
