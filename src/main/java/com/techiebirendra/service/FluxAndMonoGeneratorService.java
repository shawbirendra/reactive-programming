package com.techiebirendra.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux(){
        return Flux.fromIterable(List.of("Birendra", "Shyam", "Radha","nn")).log();//real data would be coming from db or remote service call
    }

    public Flux<String> namesFluxMap(){
        return Flux.fromIterable(List.of("Birendra", "Shyam", "Radha"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > 5)
                .doOnNext(name ->{
                    System.out.println("Name is : "+name);
                })
                .doOnComplete(()->{
                    System.out.println("completed");
                })
                .log();
    }

    public Flux<String> namesFluxFlatMap(){
        return Flux.fromIterable(List.of("Biru","Ram"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > 2)
                .flatMap(this::splitString)
                .log();
    }

    Function<Flux<String>, Flux<String>> transformFun = name -> name.map(String::toUpperCase).filter(s->s.length()>2);
    public Flux<String> namesFluxMapTransform(){
        return Flux.fromIterable(List.of("Biru","Ram"))
                .transform(transformFun)
                .log();
    }
    public Flux<String> namesFluxFlatMapWithDelayAsync(){
        return Flux.fromIterable(List.of("Biru","Ram"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > 2)
                .flatMap(this::splitStringWithDelayAsync)
                .log();
    }

    public Flux<String> namesFluxConcatMap(){
        return Flux.fromIterable(List.of("Biru","Ram"))
                .map(String::toUpperCase)
                .filter(name -> name.length() > 2)
                .concatMap(this::splitStringWithDelayAsync)
                .log();
    }

    public Flux<String> splitString(String name){
        var namesArray = name.split("");
        return Flux.fromArray(namesArray);
    }

    public Flux<String> splitStringWithDelayAsync(String name){
        var random = new Random().nextInt(1000);
        var namesArray = name.split("");
        return Flux.fromArray(namesArray).delayElements(Duration.ofMillis(random));
    }

    public Flux<String> namesFluxMapImmutable(){
        var fluxImmutable = Flux.fromIterable(List.of("Birendra", "Shyam", "Radha")).log();
        fluxImmutable.map(String::toUpperCase);
        return fluxImmutable;

    }
    public Mono<String> nameMono(){
        return Mono.just("Pappu");
    }

    public Mono<List<String>> namesMonoFlatMap(){
        return Mono.just("Biru")
                .map(String :: toUpperCase)
                .filter(s->s.length()>3)
                .flatMap(this::nameSplitMono)
                .log();
    }

    public Flux<String> namesMonoFlatMapMany(){
        return Mono.just("Biru")
                .map(String :: toUpperCase)
                .filter(s->s.length()>3)
                .flatMapMany(this::nameSplitMonoMany)
                .log();
    }

    private Mono<List<String>> nameSplitMono(String name) {
        var charArray = name.split("");
        var charList = List.of(charArray);
        return Mono.just(charList);
    }

    private Flux<String> nameSplitMonoMany(String name) {
        var charArray = name.split("");
        return Flux.fromArray(charArray);
    }

    public Flux<String> explore_concat(){
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return Flux.concat(abcFlux, defFlux).log();
    }
    public Flux<String> explore_concatWith(){
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> explore_concatWithMono(){
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.concatWith(bMono).log();
    }

    public Flux<String> explore_merge(){
        var abcFlux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(120));
        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> explore_mergeWith(){
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");
        return aMono.mergeWith(bMono).log();
    }

    public Flux<String> explore_mergeSequential(){
        var abcFlux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(120));
        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public Flux<String> explore_zip(){
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        return Flux.zip(abcFlux, defFlux,(a, b) -> a+b).log();
    }

    public Flux<String> explore_zip2(){
        var abcFlux = Flux.just("A","B","C");
        var defFlux = Flux.just("D","E","F");
        var numFlux1 = Flux.just("1","2","3");
        var numFlux2 = Flux.just("4","5","6");
        return Flux.zip(abcFlux, defFlux,numFlux1, numFlux2)
                .map(t4->t4.getT1()+t4.getT2()+t4.getT3()+t4.getT4()).log();
    }

    public Flux<String> exception_flux(){
        return Flux.just("A","B","C")
                .concatWith(Flux.error(new RuntimeException("Exception thrown")))
                .concatWith(Flux.just("D"))
                .log();
    }
    public static void main(String[] args) {

        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> {
                    System.out.println("Name is : "+ name);
                });

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> {
                    System.out.println("Mono name is : "+ name);
                });
    }
}
