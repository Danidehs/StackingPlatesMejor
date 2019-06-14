package stackingplatesmejor;

/**
 * @author Daniel Hernandez y Jhon Jairo Riascos
 */

import java.util.Scanner;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class StackingPlatesMejor {

    public List<Deque<Integer>> crearPilas(){
    Scanner teclado = new Scanner(System.in);
    List<Deque<Integer>> listaDePilasDePlatos = new ArrayList<>();
    
    int numeroDePilas = teclado.nextInt();
    while((numeroDePilas -= 1) >= 0){
      Deque<Integer> pilaDePlatos = new ArrayDeque<>();
      
      int numeroDePlatos = teclado.nextInt();
      while((numeroDePlatos -= 1) >= 0)
        pilaDePlatos.push(teclado.nextInt());
      
      listaDePilasDePlatos.add(pilaDePlatos);
    }
    return listaDePilasDePlatos;
  }

  public void imprimirListaDePilasDePlatos(List<Deque<Integer>> listaPlatos){
    for(int i = 0; i < listaPlatos.size(); ++i){
      System.out.println(listaPlatos.get(i));
    }
  }
  
  public int obtenerIndiceDePlatosParaLaPrimerPila(List<Deque<Integer>> listaPlatos, int posPila1, int posPila2){
    return (listaPlatos.get(posPila1).peek() <= listaPlatos.get(posPila2).peek())?
            listaPlatos.get(posPila1).peek() : listaPlatos.get(posPila2).peek();
  }

  public boolean obtenerPilaConElPlatoMayorEsLaPila1(List<Deque<Integer>> listaPlatos, int posPila1, int posPila2){
    return (listaPlatos.get(posPila1).peek() > listaPlatos.get(posPila2).peek());
  }

  public int apilar(List<Deque<Integer>> listaPlatos, int posPila1, int posPila2){
    Deque<Integer> pilaDePlatosCompleta = new ArrayDeque<>();
    Deque<Integer> pila1, pila2;
    pila1 = listaPlatos.get(posPila1);
    pila2 = listaPlatos.get(posPila2);
    int cont = 0;
    
    
    while(!pila1.isEmpty() && !pila2.isEmpty()){
        if((Objects.equals(pila1.peekFirst(), pila1.peekLast())) 
                && (Objects.equals(pila2.peekFirst(), pila2.peekLast()))) {
            pilaDePlatosCompleta.addAll(pila1);
            pila1.clear();

            if (pila1.isEmpty()) break;
        } else {

            int indiceDePlatosParaLaPrimerPila = obtenerIndiceDePlatosParaLaPrimerPila(listaPlatos, posPila1, posPila2);
            boolean pilaConElPlatoMayorEsLaPila1 = obtenerPilaConElPlatoMayorEsLaPila1(listaPlatos,posPila1, posPila2);
            int plato = 0;
            
            
            while(!pila1.isEmpty() && !pila2.isEmpty() &&
                    ((plato = (pilaConElPlatoMayorEsLaPila1)?
                    pila1.peek() : pila2.peek())) >= indiceDePlatosParaLaPrimerPila){
                
                if(pilaConElPlatoMayorEsLaPila1) pila1.pop();
                else pila2.pop();
                
                pilaDePlatosCompleta.push(plato);
                cont++;
            }
            if(!pila1.isEmpty() && !pila2.isEmpty()){
                
                int siguientePlatoEsParteDelConjunto =
                        ((!pilaConElPlatoMayorEsLaPila1)? pila1.pop(): pila2.pop());
                pilaDePlatosCompleta.push(siguientePlatoEsParteDelConjunto);
                
                while((!pila1.isEmpty()  && !pila2.isEmpty()) &&
                        (plato = ((!pilaConElPlatoMayorEsLaPila1)? pila1.peek(): pila2.peek()) ) +1
                        == siguientePlatoEsParteDelConjunto){
                    
                    if(!pilaConElPlatoMayorEsLaPila1 )
                        pila1.pop();
                    else  
                        pila2.pop();
                    pilaDePlatosCompleta.push(plato);
                    siguientePlatoEsParteDelConjunto = plato; 
                    cont++;
                }
            }
            cont++;
        }
    }
    
    while(!pila1.isEmpty() && pila2.isEmpty()){
        pilaDePlatosCompleta.push(pila1.pop());
        if (pila1.isEmpty())   cont++;
    }
    
    while(!pila2.isEmpty() && pila1.isEmpty()){
        pilaDePlatosCompleta.push(pila2.pop());
        if (pila2.isEmpty())    cont++;    
    } 
    
    return cont;
  }
  
  public static void main(String[] args) {
    StackingPlatesMejor miClase = new StackingPlatesMejor();
    List<Deque<Integer>> miLista = miClase.crearPilas();
    int contador = 0;
    
    for(int i = 1; i < miLista.size(); ++i){
      contador = contador + miClase.apilar(miLista, i-1, i);
    }
    System.out.println("No. Operaciones: " + contador);
  }

}
