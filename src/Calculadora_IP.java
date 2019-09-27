import java.util.Scanner;

/*
 membros do grupo:
 gabriel grazziani barbosa
 Matheus Fernandes Gomes
 Raffael Aparecido da Silva Monteiro
 Eduardo Loredo Peixoto
 */

public class Calculadora_IP {
	public static void main(String[] args) {
		int menu = 1;
		Scanner leia = new Scanner(System.in);
		System.out.println("----------------------------------------------------");
		while (menu!=5) {
			System.out.println("MENU");
		    System.out.println();
		    System.out.println("1 - Comparação de IPs padão");
		    System.out.println("2 - Identificação dos Endereçõs de Rede, Broadcast e IPs");
		    System.out.println("3 - Cálculo de SubRede");
		    System.out.println("4 - Identificar a classe do IP");
		    System.out.println("5 - Sair");
		    System.out.println();
		    menu = 0;
		    while ((menu<1) || (menu>5)) {		
			    System.out.print("Opção: ");
			    menu = leia.nextInt();
		    }    
		    System.out.println("----------------------------------------------------");
		    switch (menu) {
		    case (1):
		    	comparacao();
		        break;
		    case (2):
		    	detalhesIP();
		        break;  
		    case (3):
		    	subRede();
		        break;
		    case (4) :
		    	mostraClasse ();
		        break;
		    }
		}
		System.out.println("Fim da execução do programa");

	}
	
	static void mostraClasse () {
		String classe = verificaClasse(leIP("",2)); 
		if (classe == "z") {
			System.out.println("Esse é um endereço de loopback.");
		}
		else if (classe == "x") {
			System.out.println("Esse é um endereço de IP reservado.");
		}
		else {
			System.out.println("Esse IP pertence a classe " + classe.toUpperCase() + ".");  
		}
		System.out.println("----------------------------------------------------");
	}
	
	
	static void comparacao () { // comparacao de IPs
		String ips1[] = leIP("primeiro",2);
		String ips2[] = leIP("segundo",2);
	    String classeIP1 = verificaClasse(ips1);
	    String classeIP2 = verificaClasse(ips2);
	    if (classeIP1 == classeIP2) {
	    	    if ((classeIP1 == "a") || (classeIP1 == "b") || (classeIP1 == "c")) {
				    	if (classeIP1 == "a") {
				    		if (Integer.parseInt(ips1[0]) == Integer.parseInt(ips2[0])) {
				       			System.out.println("Os dois IPs pertencem a classe A, e pertencem a mesma rede!");
				    		}
				    		else {
				    			System.out.println("Os dois IPs pertencem a classe A, mas não pertencem a mesma rede!");
				
				    		}
				    	}
				    	else if (classeIP1 == "b") {
				    		if ((Integer.parseInt(ips1[0]) == Integer.parseInt(ips2[0])) && (Integer.parseInt(ips1[1]) == Integer.parseInt(ips2[1]))) {
				    			System.out.println("Os dois IPs pertencem a classe B, e pertencem a mesma rede!");
				    		}
				    		else {
				    			System.out.println("Os dois IPs pertencem a classe B, mas não pertencem a mesma rede!");
				    		}
				    	}
				    	else {
				    		if ((Integer.parseInt(ips1[0]) == Integer.parseInt(ips2[0])) && (Integer.parseInt(ips1[1]) == Integer.parseInt(ips2[1])) && (Integer.parseInt(ips1[2]) == Integer.parseInt(ips2[2]))) {
				    			System.out.println("Os dois IPs pertencem a classe C, e pertencem a mesma rede!");
				    		}
				    		else {
				    			System.out.println("Os dois IPs pertencem a classe C, mas não pertencem a mesma rede!");
				    		}
				    	}
		        }
		    	else {
		    		switch(classeIP1) {
		    		case("d"):
		    			System.out.println("Os dois IPs pertencem a classe D (multicash).");
		    			break;
		    		case("e"):
		    			System.out.println("Os dois IPs pertencem a classe E.");
		    			break;
		    		case("z"):
		    			System.out.println("Os dois IPs são endereços de loopback.");
		    			break;
		    		case("x"):
		    			System.out.println("Os dois IPs são endereços de IP reservados.");
		    			break;	
		    		}
		    	}
	    }
	    else {
	    	System.out.println("Os IPs não pertencem a mesma classe e por consequência não pertencem a mesma rede!");
	    }
	    System.out.println("----------------------------------------------------");
	}

	 static void detalhesIP () { // Identificação dos Endereçõs de Rede, Broadcast e IPs
		String ip[] = leIP("",1);
		String classe = verificaClasse(ip);
        String mascara[] = leMascara(classe);
        String rede[] = enderecoDeRede(ip,mascara);	
		int nSubRede = numeroSubRede(mascara,classe);   
		if (classe == "c") {  // classe c
			    int qHost = 256/nSubRede;
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println("Rede...............: "+rede[0] + "." + rede[1] + "." + rede[2] + "." + rede[3] );
				System.out.println("Endereço inicial...: "+ rede[0] + "."+ rede[1] + "."+ rede[2]+ "." + (Integer.parseInt(rede[3])+1) );
				System.out.println("Endereço final.....: "+ rede[0] + "."+ rede[1] + "."+ rede[2]+ "." +(Integer.parseInt(rede[3])+(qHost-2)));
				System.out.println("Endereço Broadcast.: "+ rede[0] + "."+ rede[1] + "."+ rede[2]+ "." +(Integer.parseInt(rede[3])+(qHost-1)));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
		}
		else if (classe == "b") { // classe B
				int p2 = Integer.parseInt(rede[2]);
				int broad;
				int qHostOriginal = 65536/nSubRede;
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println("Rede...............: "+ rede[0] + "." + rede[1] + "." + rede[2] + "." + rede[3]);
				System.out.println("Endereço inicial...: "+ rede[0] + "."+ rede[1] + "."+ rede[2]+ "." +(Integer.parseInt(rede[3])+1));
				broad = qHostOriginal + Integer.parseInt(rede[3]);
				while (broad>256) {
					broad += -256;
					p2++;		
				}
				System.out.println("Endereço final.....: "+ rede[0] + "."+ rede[1] + "."+ p2+ "." +(broad-2));
				System.out.println("Endereço Broadcast.: "+ rede[0] + "."+ rede[1] + "."+ p2+ "." +(broad-1));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
			
		}
		else {  //classe A
				int p2 = Integer.parseInt(rede[2]);
				int p1 = Integer.parseInt(rede[1]);
				int broad ;
				int qHostOriginal = 16777216/nSubRede;
				System.out.println("----------------------------------------------------");
				System.out.println("Rede...............: "+ rede[0] + "." + rede[1] + "." + rede[2] + "." + rede[3]);
				System.out.println("Endereço inicial...: "+ rede[0] + "."+ p1 + "."+ p2+ "." +(Integer.parseInt(rede[3])+1));
				broad = qHostOriginal + Integer.parseInt(rede[3]);
				while (broad>256) {
					broad += -256;
					p2++;		
				}
				while (p2>=256) {
					p2 += -256;
					p1++;		
				}
				System.out.println("Endereço final.....: "+ rede[0] + "."+ p1 + "."+ p2+ "." +(broad-2));
				System.out.println("Endereço Broadcast.: "+ rede[0] + "."+ p1 + "."+ p2+ "." +(broad-1));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
				
		}	
	}
	 
	static void subRede (){   //cálculo de subRede
		String ips1[] = leIP("",1);
		String classe = verificaClasse(ips1);
		String mascara[] = leMascara(classe);	
		int nSubRede = numeroSubRede(mascara,classe);   
		int rede= 0;
		System.out.println();
		System.out.println("Total de SubRedes...: " + nSubRede);
		System.out.println();
		if (classe == "c") {  // classe c
			int qHostOriginal = 256/nSubRede;
			System.out.println("Numero total de ips em cada SubRede...: " + qHostOriginal);
			System.out.println();
			System.out.println("Numero total de ips validos para endereçamento, em cada SubRede...: " + (qHostOriginal-2));
			System.out.println();
			for (int i= 0; i<nSubRede;i++) {
		
				System.out.println("----------------------------------------------------");
				System.out.println("SubRede: "+ (i+1));
				System.out.println();
				System.out.println("Rede...............: "+ ips1[0] + "."+ ips1[1] + "."+ ips1[2]+ "." +rede);
				System.out.println("Endereço inicial...: "+ ips1[0] + "."+ ips1[1] + "."+ ips1[2]+ "." +(rede+1));
				rede += qHostOriginal;
				System.out.println("Endereço final.....: "+ ips1[0] + "."+ ips1[1] + "."+ ips1[2]+ "." +(rede-2));
				System.out.println("Endereço Broadcast.: "+ ips1[0] + "."+ ips1[1] + "."+ ips1[2]+ "." +(rede-1));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
			}
		}
		else if (classe == "b") { // classe B
			int qHostOriginal = 65536/nSubRede;
			System.out.println("Numero total de ips em cada SubRede...: " + qHostOriginal);
			System.out.println();
			System.out.println("Numero total de ips validos para endereçamento, em cada SubRede...: " + (qHostOriginal-2));
			System.out.println();
			for (int i= 0; i<nSubRede;i++) {								
				System.out.println("----------------------------------------------------");
				System.out.println("SubRede: "+(i+1));
				System.out.println();
				System.out.println("Rede...............: "+ ips1[0] + "."+ ips1[1] + "."+ ((int) rede/256) + "." +(rede%256));
				System.out.println("Endereço inicial...: "+ ips1[0] + "."+ ips1[1] + "."+ ((int) (rede+1)/256) + "." +((rede+1)%256));
				rede += qHostOriginal;
				System.out.println("Endereço final.....: "+ ips1[0] + "."+ ips1[1] + "."+ ((int) (rede-2)/256) + "." +((rede-2)%256));
				System.out.println("Endereço Broadcast.: "+ ips1[0] + "."+ ips1[1] + "."+ ((int) (rede-1)/256) + "." +((rede-1)%256));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();	
			}	
		}
		else {  //classe A
			int qHostOriginal = 16777216/nSubRede;
			System.out.println("Numero total de ips em cada SubRede...: " + qHostOriginal);
			System.out.println();
			System.out.println("Numero total de ips validos para endereçamento, em cada SubRede...: " + (qHostOriginal-2));
			System.out.println();
			for (int i= 0; i<nSubRede;i++) {
				System.out.println("----------------------------------------------------");
				System.out.println("SubRede: "+(i+1));
				System.out.println();
				System.out.println("Rede...............: "+ ips1[0] + "."+ ((int) rede/65536) + "."+ (((int) rede/256)%256)+ "." +(rede%256));
				System.out.println("Endereço inicial...: "+ ips1[0] + "."+ ((int) (rede+1)/65536) + "."+ (((int) (rede+1)/256)%256)+ "." +((rede+1)%256));
				rede += qHostOriginal;
				System.out.println("Endereço final.....: "+ ips1[0] + "."+ ((int) (rede-2)/65536) + "."+ (((int) (rede-2)/256)%256)+ "." +((rede-2)%256));
				System.out.println("Endereço Broadcast.: "+ ips1[0] + "."+ ((int) (rede-1)/65536) + "."+ (((int) (rede-1)/256)%256)+ "." +((rede-1)%256));
				System.out.println("----------------------------------------------------");
				System.out.println();
				System.out.println();
			}	
		}
	}

	static String[] leIP(String texto, int nivel) {
		Scanner leia = new Scanner(System.in);
		String ips[], mudar;
		boolean ipVCoreto = 2==3;
		mudar = "";
		ips = mudar.split(";");
		ips[0] = "";
		while (!ipVCoreto){
			System.out.print("Qual o "+texto+" IP: ");
			String ip1 = leia.nextLine();
			mudar = ip1.replace(".", ";");
			ips = mudar.split(";");
			
		    ipVCoreto = verificaIP(ips,nivel);
			
		}
		return(ips);
	}

	static String[] leMascara (String classe) {
		Scanner leia = new Scanner(System.in);
		String  mudar, mascara[];
		mudar = "";
		mascara = mudar.split(";");
		mascara[0] = "";
		boolean mascaraVCoreto = 2==3;
		while (!mascaraVCoreto){  //recebe uma mascara e verifica se esta coreta
			System.out.print("Qual a mascara desse IP: ");
			String mascaraTesto = leia.nextLine();
			mudar = mascaraTesto.replace(".", ";");
			mascara = mudar.split(";");
		    mascaraVCoreto = verificaMascara(mascara,classe);		    
		}
		return(mascara);
	}

	static String[] enderecoDeRede (String ipB[], String mascaraB[]) { // ip e mascara devem ser na base 10
		ipB = binario(ipB);
		
		mascaraB = binario(mascaraB);
		
		String redeB[] = new String [4];
		String rede10[] = new String [4];
		for (int k = 0; k<4; k++) {  		
			redeB[k] = "";
		    for (int j =0; j<=7; j++) {
		    	String a =  String.valueOf(mascaraB[k].charAt(j)); 
		    	String b =  String.valueOf(ipB[k].charAt(j));
		    	//System.out.println(a*b+"g");    
	            redeB[k] += String.valueOf(Integer.parseInt(a)*Integer.parseInt(b));  
			}  
		} 
		
		for (int k = 0; k<4; k++) {
			rede10[k] = "0";
			//System.out.println(redeB[k]);
			int numero = 128;
			for (int j =0; j<=7; j++) {
				//System.out.println(redeB[k].charAt(j) == 1);
				String a =  String.valueOf(redeB[k].charAt(j));
	            if (Integer.parseInt(a) == 1) {
	            	rede10[k] = String.valueOf(Integer.parseInt(rede10[k])+numero);
	            }
	            numero =  numero/2; 
			}
			//System.out.println(rede10[k]);
		}
		//System.out.println(rede10[0]);
		return(rede10);
	}

	static String verificaClasse (String ip[]) {
	    if (( Integer.parseInt(ip[0])> 0) && ( Integer.parseInt(ip[0]) <= 126)) {
	    	return("a");
	    	//System.out.println("a");
	    }
	    else if (( Integer.parseInt(ip[0]) >= 128) && ( Integer.parseInt(ip[0]) <= 191)) {
	    	return("b");
	    	//System.out.println("b");
	    }
	    else if (( Integer.parseInt(ip[0]) >= 192) && ( Integer.parseInt(ip[0]) <= 223)) {
	    	return("c");
	    	//System.out.println("c");
	    }
	    else if (( Integer.parseInt(ip[0]) >= 224) && ( Integer.parseInt(ip[0]) <= 239)) {
	    	return("d");
	    }
	    else if (( Integer.parseInt(ip[0]) >= 240) && ( Integer.parseInt(ip[0]) <= 255)) {
	    	return("e");
	   	}
	    else if(Integer.parseInt(ip[0]) == 127)  {
	    	return("z"); //loopback
	    }
	    else {
	    	return("x"); //endereço de IP reservado
	    }
	}

	static boolean verificaIP (String ip[], int nivel) {  // nivel 1 a b c // nivel 2 a b c d e z=loopback 
		boolean verifica = (2==3);
		if  (((ip.length==4) && 
		        ( Integer.parseInt(ip[0]) >= 0) && ( Integer.parseInt(ip[0]) <= 255) &&  
		    	( Integer.parseInt(ip[1]) >= 0) && ( Integer.parseInt(ip[1]) <= 255)&&
		    	( Integer.parseInt(ip[2]) >= 0) && ( Integer.parseInt(ip[2]) <= 255)&&
		    	( Integer.parseInt(ip[3]) >= 0) && ( Integer.parseInt(ip[3]) <= 255))) {
		    	if (nivel== 2) {
		    		verifica = (2==2);
		    	}
		    	else {
		    		String classe = verificaClasse(ip);
		    		switch(classe) {
		    		case ("a"):
		    		case ("b"):
		    		case ("c"):
		    			verifica = (2==2);
		    		    break;
		    		case ("d"):	
		    		case ("e"):
		    			System.out.println("Endereços de classe "+ classe.toUpperCase() +" não são aceitos para essa seção. ");
	    		        System.out.println("Por favor forneça um outro IP valido. ");
	    		        verifica = (2==3);
	    		        break;
		    		case ("z"):
		    			System.out.println("Endereços loopback não são aceitos para essa seção. ");
	    		        System.out.println("Por favor forneça um outro IP valido. ");
	    		        verifica = (2==3);
	    		        break;
		    		case ("x"):
		    			System.out.println("Esse e um endereço IP reservado que não e aceitos para essa seção. ");
	    		        System.out.println("Por favor forneça um outro IP valido. ");
	    		        verifica = (2==3);
	    		        break;    
		    		
		    		}
		    			
		    	}
		}
		else {
			System.out.println("IP invalido ou não de acordo com os padroes aceitos pela calculadora!");
			System.out.println("Por favor forneça um IP valido. ");
			verifica = (2==3);
		}
		return (verifica);
		
	}

	static boolean verificaMascara (String mas[], String classe) {
		     if ((mas.length==4) &&  (Integer.parseInt(mas[0]) >= Integer.parseInt(mas[1])) && (Integer.parseInt(mas[1]) >= Integer.parseInt(mas[2])) && (Integer.parseInt(mas[2]) >= Integer.parseInt(mas[3]) && !((Integer.parseInt(mas[0]) != 255) && (Integer.parseInt(mas[1]) != 0)) && !((Integer.parseInt(mas[1]) != 255) && (Integer.parseInt(mas[2]) != 0)) && !((Integer.parseInt(mas[2]) != 255) && (Integer.parseInt(mas[3]) != 0)) )) {
		    	   switch (classe) {
		    	     case ("a"):
				        if(!( 
					        ( (Integer.parseInt(mas[0]) == 255)) && 
					        (( Integer.parseInt(mas[1]) == 0) || ( Integer.parseInt(mas[1]) == 255) || ( Integer.parseInt(mas[1]) == 128)|| ( Integer.parseInt(mas[1]) == 192)|| ( Integer.parseInt(mas[1]) == 224)|| ( Integer.parseInt(mas[1]) == 240)|| ( Integer.parseInt(mas[1]) == 248)|| (Integer.parseInt(mas[1]) == 252) || (Integer.parseInt(mas[1]) == 254))&& 
					        (( Integer.parseInt(mas[2]) == 0) || ( Integer.parseInt(mas[2]) == 255) || ( Integer.parseInt(mas[2]) == 128)|| ( Integer.parseInt(mas[2]) == 192)|| ( Integer.parseInt(mas[2]) == 224)|| ( Integer.parseInt(mas[2]) == 240)|| ( Integer.parseInt(mas[2]) == 248)|| ( Integer.parseInt(mas[2]) == 252) || (Integer.parseInt(mas[1]) == 254))&&
					        (( Integer.parseInt(mas[3]) == 0) || ( Integer.parseInt(mas[3]) == 128)|| ( Integer.parseInt(mas[3]) == 192)|| ( Integer.parseInt(mas[3]) == 224)|| ( Integer.parseInt(mas[3]) == 240)|| ( Integer.parseInt(mas[3]) == 248)|| ( Integer.parseInt(mas[3]) == 252)))) {	
					        	System.out.println("Mascara invalido ou fora do padrão aceito pela calculadora!");
					        	return (2==3);
				        }
				        else {	
				            return (2==2);
				        }
		    	     case ("b"):
		    	    	if(!( (Integer.parseInt(mas[0]) == 255) && 
							  (Integer.parseInt(mas[1]) == 255) && 
							(( Integer.parseInt(mas[2]) == 0) || ( Integer.parseInt(mas[2]) == 255) || ( Integer.parseInt(mas[2]) == 128)|| ( Integer.parseInt(mas[2]) == 192)|| ( Integer.parseInt(mas[2]) == 224)|| ( Integer.parseInt(mas[2]) == 240)|| ( Integer.parseInt(mas[2]) == 248)|| ( Integer.parseInt(mas[2]) == 252)|| (Integer.parseInt(mas[1]) == 254))&&
							(( Integer.parseInt(mas[3]) == 0) || ( Integer.parseInt(mas[3]) == 128)|| ( Integer.parseInt(mas[3]) == 192)|| ( Integer.parseInt(mas[3]) == 224)|| ( Integer.parseInt(mas[3]) == 240)|| ( Integer.parseInt(mas[3]) == 248)|| ( Integer.parseInt(mas[3]) == 252)))) {	
							        	System.out.println("Mascara invalido ou fora do padrão aceito pela calculadora!");
							        	return (2==3);
						}
						else {	
						     return (2==2);
						}
				     default:
				    	 if(!( (Integer.parseInt(mas[0]) == 255) && 
							   (Integer.parseInt(mas[1]) == 255) && 
							   (Integer.parseInt(mas[2]) == 255) &&
							 (( Integer.parseInt(mas[3]) == 0) || ( Integer.parseInt(mas[3]) == 128)|| ( Integer.parseInt(mas[3]) == 192)|| ( Integer.parseInt(mas[3]) == 224)|| ( Integer.parseInt(mas[3]) == 240)|| ( Integer.parseInt(mas[3]) == 248)|| ( Integer.parseInt(mas[3]) == 252)))) {	
							        System.out.println("Mascara invalido ou fora do padrão aceito pela calculadora!");
							        return (2==3);
						}
						else {	
						     return (2==2);
						}
		    	   }    
		     }
		     System.out.println("Mascara invalido ou fora do padrão aceito pela calculadora!");
	         return (2==3);
	}

	static String[] binario (String numeroBase10[]) { //determina o binario
		int  dividendo, resto; 
		String numeroBase2 ;
		String biIp [] = new String [4] ;
		for (int k = 0; k<4; k++) {
			biIp[k] = "";
			dividendo = Integer.parseInt(numeroBase10[k]);
		    numeroBase2 = "";
		    for (int j =1; j<=8; j++) {
				resto = dividendo % 2; 
				dividendo = dividendo / 2;
				//System.out.print(resto);
				numeroBase2 = numeroBase2 + String.valueOf(resto);
			}
			for (int i = numeroBase2.length() -1; i>=0;i = i -1) {
				biIp[k] +=  String.valueOf(numeroBase2.charAt(i));
				
			}
			
		}
		//System.out.println(biIp[0] +"."+biIp[1] +"."+biIp[2] +"."+biIp[3] +".");
		return(biIp);
	}

	static int numeroSubRede (String mascara[],String classe) {
		mascara = binario(mascara);
		int nSubRede = 0;
		for (int i = 0; i < mascara[3].length(); i++) {
			if(mascara[3].charAt(i)== '1'){
				nSubRede ++;
			}
		}
		if (classe=="b" || classe=="a") {
			for (int i = 0; i < mascara[2].length(); i++) {
				if(mascara[2].charAt(i)== '1'){
					nSubRede ++;
				}
			}	
		}
		if ( classe=="a") {
			for (int i = 0; i < mascara[1].length(); i++) {
				if(mascara[1].charAt(i)== '1'){
					nSubRede ++;
				}
			}	
		}
		return((int)Math.pow(2,nSubRede));
	}


}
