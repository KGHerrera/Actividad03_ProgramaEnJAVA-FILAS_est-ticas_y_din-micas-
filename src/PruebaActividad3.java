import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Impresion{
	private int identificador;
	private double tama�o;
	private int hojas;
	
	public Impresion(int identificador, double tama�o, int hojas) {
		super();
		this.identificador = identificador;
		this.tama�o = tama�o;
		this.hojas = hojas;
	}
	
	public Impresion() {
		
	}
	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public double getTama�o() {
		return tama�o;
	}

	public void setTama�o(double tama�o) {
		this.tama�o = tama�o;
	}

	public int getHojas() {
		return hojas;
	}

	public void setHojas(int hojas) {
		this.hojas = hojas;
	}

	@Override
	public String toString() {
		return "Impresion [identificador=" + identificador + ", tama�o=" + tama�o + ", hojas=" + hojas + "]";
	}
}

interface RegistroImpreciones{
	public Impresion imprimirSacarElemento();
	public boolean agregarColaImprecion(Impresion dato);
	public void mostrarTotalHojasImpresas();
	public double mostrarCantidadBytesRecibidos();
}

class ImplementacionFilaEstatica implements RegistroImpreciones{
	private Impresion datosImpresion[];
	private int frente, fin;
	private int totalHojasImpresas;
	
	
	
	public ImplementacionFilaEstatica(int tama�o) {
		this.datosImpresion = new Impresion[tama�o];
		frente = fin = -1;
	}

	public boolean verificarFilaLlena() {
		return fin == datosImpresion.length-1;
		}
		
	public boolean verificarFilaVacia() {
		return fin == -1;
	}
		
	public Impresion imprimirSacarElemento() {
		if(!verificarFilaVacia()) {
			Impresion impresionBorrada = datosImpresion[0];
			for (int i = 0; i < datosImpresion.length-1; i++) {
				datosImpresion[i] = datosImpresion[i+1];
			}
			datosImpresion[datosImpresion.length-1] = new Impresion();
			fin-=1;
			totalHojasImpresas += impresionBorrada.getHojas();
			return impresionBorrada;
		} else {
			frente = -1;
			return null;
		}
	}
	
	public boolean agregarColaImprecion(Impresion dato) {
		if(!verificarFilaLlena()) {
			 if(frente == -1 && fin == -1) {
				 frente++;
				 fin++;
				 datosImpresion[fin] = dato;
				 return true;
			 } else {
				 fin++;
				 datosImpresion[fin] = dato;
				 return true;
			 }
		} else {
			return false;
		}
	}
	
	public void mostrarTotalHojasImpresas() {
		System.out.println("\nTotal de hojas impresas: " + totalHojasImpresas);
	}
	
	public double mostrarCantidadBytesRecibidos() {
		double totalBytes = 0;
		if(!verificarFilaVacia()) {
			for (int i = 0; i <= fin; i++) {
				totalBytes += datosImpresion[i].getTama�o();
			}
		}
		return totalBytes;
	}
	
}

class ImplementacionFilaDinamica implements RegistroImpreciones{
	Queue<Impresion> datosImpresion = new LinkedList<Impresion>();
	private int totalHojas;
	public boolean verificarFilaVacia() {
		return datosImpresion.size() == 0;
	}
	
	public Impresion imprimirSacarElemento() {
		if(!verificarFilaVacia()) {
			
			Impresion datoBorrar = datosImpresion.peek();
			totalHojas += datoBorrar.getHojas();
			datosImpresion.poll();
			return datoBorrar;
		}else {
			return null;
		}
	}
	public boolean agregarColaImprecion(Impresion dato) {
		if(datosImpresion.add(dato)) {
			return true;
		} else {
			return false;
		}
	}
	public void mostrarTotalHojasImpresas() {
		System.out.println("Total de hojas impresas: " + totalHojas);
	}
	public double mostrarCantidadBytesRecibidos() {
		double totalBytes = 0;
		if(!verificarFilaVacia()) {
			for (Impresion impresion : datosImpresion) {
				totalBytes += impresion.getTama�o();
			}
			return totalBytes;
		} else {
			return 0.0;
		}
	}
}

public class PruebaActividad3 {
	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		
		System.out.println("Introduce tama�o de la fila estatica: ");
		int tama�o = entrada.nextInt();
		ImplementacionFilaEstatica ife = new ImplementacionFilaEstatica(tama�o);
		int identificador = 0;
		int opcion = 0;
		
		do {
			System.out.println("\nElije una de las siguientes opciones");
			System.out.println("1) Agregar cola imprimir");
			System.out.println("2) Imprimir");
			System.out.println("3) Mostrar el total de hojas ya impresas");
			System.out.println("4) Mostrar Cantidad de bytes recibidos");
			System.out.println("5) Salir");
			System.out.println("Introduce opcion: ");
			opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				System.out.println("Introduce tama�o: ");
				double tama�oImpresion = entrada.nextInt();
				System.out.println("Introduce numero de hojas: ");
				int hojas = entrada.nextInt();
				identificador++;
				
				Impresion dato = new Impresion(identificador, tama�oImpresion, hojas);
				
				if(ife.agregarColaImprecion(dato)) {
					System.out.println("\nSe agrego correctamente");
				} else {
					System.out.println("\nFila llena");
					identificador--;
				}
				break;
			
			case 2:
				Impresion impreso = ife.imprimirSacarElemento();
				if (impreso != null) {
					System.out.println("\nSe imprimio correctamente el documento: " + impreso.toString());
				} else {
					System.out.println("\nFila vacia");
				}
				break;
			
			case 3:
				ife.mostrarTotalHojasImpresas();
				break;
				
			case 4:
				System.out.println("\nCantidad de bytes recibidos: " + ife.mostrarCantidadBytesRecibidos());
				break;
				
			case 5:
				System.out.println("\nSaliendo . . . ");
				break;

			default:
				System.out.println("\nOpcion incorrecta");
				break;
			}
			
		} while (opcion != 5);
		
		opcion = 0;
		ImplementacionFilaDinamica ifd = new ImplementacionFilaDinamica();
		identificador = 0;
		
		System.out.println("\nImplementacion fila dinamica: ");
		
		do {
			System.out.println("\nElije una de las siguientes opciones");
			System.out.println("1) Agregar cola imprimir");
			System.out.println("2) Imprimir");
			System.out.println("3) Mostrar el total de hojas ya impresas");
			System.out.println("4) Mostrar Cantidad de bytes recibidos");
			System.out.println("5) Salir");
			System.out.println("Introduce opcion: ");
			opcion = entrada.nextInt();
			
			switch (opcion) {
			case 1:
				System.out.println("Introduce tama�o: ");
				double tama�oImpresion = entrada.nextInt();
				System.out.println("Introduce numero de hojas: ");
				int hojas = entrada.nextInt();
				identificador++;
				
				Impresion dato = new Impresion(identificador, tama�oImpresion, hojas);
				
				if(ifd.agregarColaImprecion(dato)) {
					System.out.println("\nSe agrego correctamente");
				} else {
					System.out.println("\nFila llena");
					identificador--;
				}
				break;
			
			case 2:
				Impresion impreso = ifd.imprimirSacarElemento();
				if (impreso != null) {
					System.out.println("\nSe imprimio correctamente el documento: " + impreso.toString());
				} else {
					System.out.println("\nFila vacia");
				}
				break;
			
			case 3:
				ifd.mostrarTotalHojasImpresas();
				break;
				
			case 4:
				System.out.println("\nCantidad de bytes recibidos: " + ifd.mostrarCantidadBytesRecibidos());
				break;
				
			case 5:
				System.out.println("\nSaliendo . . . ");
				break;

			default:
				System.out.println("\nOpcion incorrecta");
				break;
			}
			
		} while (opcion != 5);
	}
}
