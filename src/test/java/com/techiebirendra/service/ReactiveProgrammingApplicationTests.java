package com.techiebirendra.service;

import com.techiebirendra.service.FluxAndMonoGeneratorService;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

class ReactiveProgrammingApplicationTests {

	FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

	@Test
	void testNamesFlux() {
		var namesFlux = fluxAndMonoGeneratorService.namesFlux();

		StepVerifier.create(namesFlux)
				.expectNextCount(4)
				.verifyComplete();
	}

	@Test
	public void testFluxNamesMap(){
		var namesFluxMap = fluxAndMonoGeneratorService.namesFluxMap();

		StepVerifier.create(namesFluxMap)
				.expectNext("BIRENDRA")
				.verifyComplete();
	}

	@Test
	public void testFluxNamesMapImmutable(){
		var namesFluxMapImmutable = fluxAndMonoGeneratorService.namesFluxMapImmutable();
		StepVerifier.create(namesFluxMapImmutable)
				.expectNext("Birendra", "Shyam", "Radha")
				.verifyComplete();
	}
	@Test
	public void namesFluxFlatMapTest(){
		var namesFluxFlatMap = fluxAndMonoGeneratorService.namesFluxFlatMap();
		StepVerifier.create(namesFluxFlatMap)
				.expectNext("B","I","R","U","R","A","M")
				.verifyComplete();
	}
	@Test
	public void namesFluxFlatMapTestWithDelay(){
		var namesFluxFlatMapAsync = fluxAndMonoGeneratorService.namesFluxFlatMapWithDelayAsync();
		StepVerifier.create(namesFluxFlatMapAsync)
				//.expectNext("B","I","R","U","R","A","M")
				.expectNextCount(7)
				.verifyComplete();
	}

	@Test
	public void namesFluxConcatMap(){
		var namesFluxFlatMapAsync = fluxAndMonoGeneratorService.namesFluxConcatMap();
		StepVerifier.create(namesFluxFlatMapAsync)
				.expectNext("B","I","R","U","R","A","M")
				///.expectNextCount(7)
				.verifyComplete();
	}

	@Test
	public void testMapNamesFlatMap(){
		var namesMonoFlatMap = fluxAndMonoGeneratorService.namesMonoFlatMap();

		StepVerifier.create(namesMonoFlatMap)
				.expectNext(List.of("B","I","R","U"))
				.verifyComplete();
	}

	@Test
	public void namesFluxFlatMapManyTest(){
		var namesFluxFlatMapMany = fluxAndMonoGeneratorService.namesMonoFlatMapMany();
		StepVerifier.create(namesFluxFlatMapMany)
				.expectNext("B","I","R","U")
				.verifyComplete();
	}

	@Test
	public void testFluxNamesMapTransform(){
		var namesFluxMapTransform = fluxAndMonoGeneratorService.namesFluxMapTransform();

		StepVerifier.create(namesFluxMapTransform)
				.expectNext("BIRU","RAM")
				.verifyComplete();
	}

	@Test
	void concatFlux() {
		var concatFlux = fluxAndMonoGeneratorService.explore_concat();
		StepVerifier.create(concatFlux)
				.expectNext("A","B","C","D","E","F")
				.verifyComplete();
	}

	@Test
	void explore_merge() {
		var merge = fluxAndMonoGeneratorService.explore_merge();
		StepVerifier.create(merge)
				.expectNext("A","D","B","E","C","F")
				.verifyComplete();
	}

	@Test
	void explore_mergeWith() {
		var merge = fluxAndMonoGeneratorService.explore_mergeWith();
		StepVerifier.create(merge)
				.expectNext("A","B")
				.verifyComplete();
	}

	@Test
	void explore_mergeSequential() {
		var mergeSequential = fluxAndMonoGeneratorService.explore_mergeSequential();
		StepVerifier.create(mergeSequential)
				.expectNext("A","B","C","D","E","F")
				.verifyComplete();
	}
	@Test
	void explore_zip() {
		var zip = fluxAndMonoGeneratorService.explore_zip();
		StepVerifier.create(zip)
				.expectNext("AD","BE","CF")
				.verifyComplete();
	}

	@Test
	void explore_zip2() {
		var zip = fluxAndMonoGeneratorService.explore_zip2();
		StepVerifier.create(zip)
				.expectNext("AD14","BE25","CF36")
				.verifyComplete();
	}

	@Test
	void exception_flux() {
		var exceptionFlux = fluxAndMonoGeneratorService.exception_flux();
		StepVerifier.create(exceptionFlux)
				.expectNext("A","B","C")
				.expectErrorMessage("Exception thrown")
				//.expectError(RuntimeException.class)
				.verify();
	}
}
