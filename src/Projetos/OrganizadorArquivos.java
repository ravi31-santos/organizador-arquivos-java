package Projetos;


import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class OrganizadorArquivos {


    private static final String PASTA_DOWNLOADS = "C:/Users/cardo/Downloads";

    public static void main(String[] args) {
        try {
            organizarArquivos();
        } catch (IOException e) {
            System.out.println("Erro ao organizar arquivos: " + e.getMessage());
        }
    }

    private static void organizarArquivos() throws IOException {

        Map<String, String> categorias = new HashMap<>();
        categorias.put("jpg", "Imagens");
        categorias.put("png", "Imagens");
        categorias.put("jpeg", "Imagens");
        categorias.put("gif", "Imagens");

        categorias.put("pdf", "Documentos");
        categorias.put("docx", "Documentos");
        categorias.put("txt", "Documentos");
        categorias.put("xlsx", "Documentos");

        categorias.put("mp3", "Músicas");
        categorias.put("wav", "Músicas");

        categorias.put("mp4", "Vídeos");
        categorias.put("avi", "Vídeos");
        categorias.put("mkv", "Vídeos");


        Path pastaDownloads = Paths.get(PASTA_DOWNLOADS);


        if (!Files.exists(pastaDownloads)) {
            System.out.println("Pasta não encontrada: " + PASTA_DOWNLOADS);
            return;
        }


        DirectoryStream<Path> stream = Files.newDirectoryStream(pastaDownloads);
        for (Path arquivo : stream) {
            if (Files.isRegularFile(arquivo)) {
                String nome = arquivo.getFileName().toString();
                String extensao = getExtensao(nome);

                String categoria = categorias.getOrDefault(extensao, "Outros");

                Path pastaDestino = pastaDownloads.resolve(categoria);


                if (!Files.exists(pastaDestino)) {
                    Files.createDirectory(pastaDestino);
                }


                Path destino = pastaDestino.resolve(nome);
                Files.move(arquivo, destino, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Arquivo \"" + nome + "\" movido para \"" + categoria + "\"");
            }
        }
    }


    private static String getExtensao(String nome) {
        int i = nome.lastIndexOf('.');
        if (i > 0 && i < nome.length() - 1) {
            return nome.substring(i + 1).toLowerCase();
        }
        return "";
    }
}

