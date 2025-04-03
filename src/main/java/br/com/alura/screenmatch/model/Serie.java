package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.traducao.ConsultaMyMemory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Getter
@Setter
@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Chave primária obrigatória

    @Column(unique = true) // titulo é único
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    @Enumerated(EnumType.STRING) // dizer que categoria é um ENUM
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;
    @OneToMany(mappedBy = "serie")   // uma série tem muitos episódios - mapeado como serie na outra classe
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(){} // necessário um construtor padrão

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0); // tenta ler como double, senão torna 0
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim()); // pega o 1 gênero
        this.atores = dadosSerie.atores();
        this.poster = dadosSerie.poster();
//        this.sinopse = ConsultaMyMemory.obterTraducao(dadosSerie.sinopse()).trim();// trim para nenhum caracter branco ou quebra de linha
        this.sinopse = dadosSerie.sinopse();
    }

    @Override
    public String toString() {
        return
                "genero=" + genero +
                ", titulo='" + titulo + '\'' +
                ", totalTemporadas=" + totalTemporadas +
                ", avaliacao=" + avaliacao +
                ", atores='" + atores + '\'' +
                ", poster='" + poster + '\'' +
                ", sinopse='" + sinopse + '\'';
    }
}
