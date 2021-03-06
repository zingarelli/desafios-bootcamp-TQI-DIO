package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.dto.QuantityDTO;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.service.BeerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static one.digitalinnovation.beerstock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {   

    //constantes para os testes
    private static final String BEER_API_URL_PATH = "/api/v1/beers";
    private static final long VALID_BEER_ID = 1L;
    private static final long INVALID_BEER_ID = 2l;
    private static final String BEER_API_SUBPATH_INCREMENT_URL = "/increment"; //aumenta a qtd em estoque
    //private static final String BEER_API_SUBPATH_DECREMENT_URL = "/decrement"; //diminui a qtd em estoque

    private MockMvc mockMvc;

    @Mock //no teste do controller, o mock ser?? o beerService
    private BeerService beerService;

    @InjectMocks //inje????o do mock 
    private BeerController beerController;

    @BeforeEach //setup do mock do controller antes de cada teste
    void setup(){
        //n??o entendi essa constru????o, mas ela simula o retorno JSON de uma API...
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                    .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                    .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                    .build();
    }

    //valida????o da cria????o de uma cerveja com sucesso
    @Test
    void whenPOSTIsCalledThenABeerIsCreated() throws Exception{
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();//cerveja de exemplo hardcoded

        //when
        //mockando a chamada ao servi??o
        when(beerService.createBeer(beerDTO)).thenReturn(beerDTO);

        //then
        //mockando o m??todo POST com um JSON com dados de cerveja para uma chamada ?? API para cria????o
        //e j?? validando com o Hamcrest
        mockMvc.perform(post(BEER_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(beerDTO)))
            .andExpect(status().isCreated()) //usando o Hamcrest
            .andExpect(jsonPath("$.name", is(beerDTO.getName())))
            .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
            .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));

    }

    //valida????o de erro quando um campo obrigat??rio (@NotNull) n??o ?? enviado
    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception{
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();//cerveja de exemplo hardcoded
        beerDTO.setBrand(null); //brand ?? um campo obrigat??rio, ir?? causar o erro

        //then
        //envia os dados com um campo obrigat??rio com dados null
        //e j?? valida o status de erro com o Hamcrest
        mockMvc.perform(post(BEER_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(beerDTO)))
            .andExpect(status().isBadRequest()); //n??o ir?? nem chamar o servi??o de cria????o
    }

    //valida????o de sucesso ao encontrar uma cerveja pelo nome
    @Test
    void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception{
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();//cerveja de exemplo hardcoded

        //when
        //mock do sucesso na chamada ao m??todo findByName()
        when(beerService.findByName(beerDTO.getName())).thenReturn(beerDTO);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH + "/" + beerDTO.getName())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) //usando o Hamcrest
            .andExpect(jsonPath("$.name", is(beerDTO.getName())))
            .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
            .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    }

    //valida????o de erro ao n??o encontrar uma cerveja pelo nome
    @Test
    void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception{
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();//cerveja de exemplo hardcoded

        //when
        //mock do erro na chamada ao m??todo findByName(), simulando que n??o encontrou o nome
        when(beerService.findByName(beerDTO.getName())).thenThrow(BeerNotFoundException.class);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH + "/" + beerDTO.getName())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()); //usando o Hamcrest
    }

    //valida????o de sucesso ao chamar o m??todo de listar todas as cervejas, retornando as cervejas
    @Test
    void whenGETListWithBeersIsCalledThenOkStatusIsReturned() throws Exception{
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); //cerveja de exemplo hardcoded

        //when
        //mock do sucesso na chamada ao m??todo listAll()
        when(beerService.listAll()).thenReturn(Collections.singletonList(beerDTO));

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()) //usando o Hamcrest, somente 1 cerveja na lista neste teste
            .andExpect(jsonPath("$[0].name", is(beerDTO.getName())))
            .andExpect(jsonPath("$[0].brand", is(beerDTO.getBrand())))
            .andExpect(jsonPath("$[0].type", is(beerDTO.getType().toString())));
    }

    //valida????o de sucesso ao chamar o m??todo de listar todas as cervejas, com lista vazia
    @Test
    void whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() throws Exception{
        //when
        //mock do sucesso na chamada ao m??todo listAll()
        when(beerService.listAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk()); //n??o retorna erro quando a lista est?? vazia
    }

     //valida????o de remo????o de cerveja com uma chamada de DELETE com o id da cerveja
     @Test
     void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception{
        //given
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); //cerveja de exemplo hardcoded 

        //when
        //o m??todo deleteById() retorna void
        doNothing().when(beerService).deleteById(beerDTO.getId());

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(BEER_API_URL_PATH + "/" + beerDTO.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent()); 
     }

    //valida????o de erro ao tentar remover uma cerveja de id n??o existente com uma chamada de DELETE
    @Test
    void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception{
        //when
        //o m??todo deleteById() neste caso gera uma exce????o
        doThrow(BeerNotFoundException.class).when(beerService).deleteById(INVALID_BEER_ID);

        //then
        mockMvc.perform(MockMvcRequestBuilders.delete(BEER_API_URL_PATH + "/" + INVALID_BEER_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound()); 
    }

    //valida????o de sucesso de uma chamada PATCH para atualizar a quantidade de cerveja
    @Test
        void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception {
        //given
        //um DTO somente com a quantidade a ser passada, j?? que ?? somente uma atualiza????o desse campo no banco
        QuantityDTO quantityDTO = QuantityDTO.builder()
            .quantity(10)
            .build();

        //mock da cerveja com a qtd atualizada
        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        beerDTO.setQuantity(beerDTO.getQuantity() + quantityDTO.getQuantity());

        //when
        //mock da resposta do incremento com sucesso
        when(beerService.increment(VALID_BEER_ID, quantityDTO.getQuantity())).thenReturn(beerDTO);

        //then
        //mock da chamada PATCH com sucesso e valida????o do sucesso na atualiza????o
        mockMvc.perform(MockMvcRequestBuilders.patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_INCREMENT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(quantityDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(beerDTO.getName())))
            .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
            .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())))
            .andExpect(jsonPath("$.quantity", is(beerDTO.getQuantity())));
    }

    //valida????o de exce????o do PATCH passando uma qtd de cerveja acima do m??ximo permitido em estoque (50)
    //eu acho que o teste n??o est?? correto, mas n??o consigo entender o que est?? faltando...
    @Test
    void whenPATCHIsCalledToIncrementGreatherThanMaxThenBadRequestStatusIsReturned() throws Exception {
        //given
        //um DTO somente com a quantidade a ser passada, j?? que ?? somente uma atualiza????o desse campo no banco
        QuantityDTO quantityDTO = QuantityDTO.builder()
            .quantity(800)
            .build();

        //then
        mockMvc.perform(MockMvcRequestBuilders.patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_INCREMENT_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(quantityDTO)))
            .andExpect(status().isBadRequest());
    }

// Comentando os c??digos que j?? vieram com o projeto baixado no desafio
    // @BeforeEach
    // void setUp() {
    //     mockMvc = MockMvcBuilders.standaloneSetup(beerController)
    //             .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
    //             .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
    //             .build();
    // }

    // @Test
    // void whenPOSTIsCalledThenABeerIsCreated() throws Exception {
    //     // given
    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

    //     // when
    //     when(beerService.createBeer(beerDTO)).thenReturn(beerDTO);

    //     // then
    //     mockMvc.perform(post(BEER_API_URL_PATH)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(beerDTO)))
    //             .andExpect(status().isCreated())
    //             .andExpect(jsonPath("$.name", is(beerDTO.getName())))
    //             .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
    //             .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    // }

    // @Test
    // void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
    //     // given
    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     beerDTO.setBrand(null);

    //     // then
    //     mockMvc.perform(post(BEER_API_URL_PATH)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(beerDTO)))
    //             .andExpect(status().isBadRequest());
    // }

    // @Test
    // void whenGETIsCalledWithValidNameThenOkStatusIsReturned() throws Exception {
    //     // given
    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

    //     //when
    //     when(beerService.findByName(beerDTO.getName())).thenReturn(beerDTO);

    //     // then
    //     mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH + "/" + beerDTO.getName())
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name", is(beerDTO.getName())))
    //             .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
    //             .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())));
    // }

    // @Test
    // void whenGETIsCalledWithoutRegisteredNameThenNotFoundStatusIsReturned() throws Exception {
    //     // given
    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

    //     //when
    //     when(beerService.findByName(beerDTO.getName())).thenThrow(BeerNotFoundException.class);

    //     // then
    //     mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH + "/" + beerDTO.getName())
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isNotFound());
    // }

    // @Test
    // void whenGETListWithBeersIsCalledThenOkStatusIsReturned() throws Exception {
    //     // given
    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

    //     //when
    //     when(beerService.listAll()).thenReturn(Collections.singletonList(beerDTO));

    //     // then
    //     mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$[0].name", is(beerDTO.getName())))
    //             .andExpect(jsonPath("$[0].brand", is(beerDTO.getBrand())))
    //             .andExpect(jsonPath("$[0].type", is(beerDTO.getType().toString())));
    // }

    // @Test
    // void whenGETListWithoutBeersIsCalledThenOkStatusIsReturned() throws Exception {
    //     // given
    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

    //     //when
    //     when(beerService.listAll()).thenReturn(Collections.singletonList(beerDTO));

    //     // then
    //     mockMvc.perform(MockMvcRequestBuilders.get(BEER_API_URL_PATH)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk());
    // }

    // @Test
    // void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
    //     // given
    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

    //     //when
    //     doNothing().when(beerService).deleteById(beerDTO.getId());

    //     // then
    //     mockMvc.perform(MockMvcRequestBuilders.delete(BEER_API_URL_PATH + "/" + beerDTO.getId())
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isNoContent());
    // }

    // @Test
    // void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
    //     //when
    //     doThrow(BeerNotFoundException.class).when(beerService).deleteById(INVALID_BEER_ID);

    //     // then
    //     mockMvc.perform(MockMvcRequestBuilders.delete(BEER_API_URL_PATH + "/" + INVALID_BEER_ID)
    //             .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isNotFound());
    // }

    // @Test
    // void whenPATCHIsCalledToIncrementDiscountThenOKstatusIsReturned() throws Exception {
    //     QuantityDTO quantityDTO = QuantityDTO.builder()
    //             .quantity(10)
    //             .build();

    //     BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     beerDTO.setQuantity(beerDTO.getQuantity() + quantityDTO.getQuantity());

    //     when(beerService.increment(VALID_BEER_ID, quantityDTO.getQuantity())).thenReturn(beerDTO);

    //     mockMvc.perform(MockMvcRequestBuilders.patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_INCREMENT_URL)
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(asJsonString(quantityDTO))).andExpect(status().isOk())
    //             .andExpect(jsonPath("$.name", is(beerDTO.getName())))
    //             .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
    //             .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())))
    //             .andExpect(jsonPath("$.quantity", is(beerDTO.getQuantity())));
    // }

//    @Test
//    void whenPATCHIsCalledToIncrementGreatherThanMaxThenBadRequestStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(30)
//                .build();
//
//        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        beerDTO.setQuantity(beerDTO.getQuantity() + quantityDTO.getQuantity());
//
//        when(beerService.increment(VALID_BEER_ID, quantityDTO.getQuantity())).thenThrow(BeerStockExceededException.class);
//
//        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_INCREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .con(asJsonString(quantityDTO))).andExpect(status().isBadRequest());
//    }

//    @Test
//    void whenPATCHIsCalledWithInvalidBeerIdToIncrementThenNotFoundStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(30)
//                .build();
//
//        when(beerService.increment(INVALID_BEER_ID, quantityDTO.getQuantity())).thenThrow(BeerNotFoundException.class);
//        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + INVALID_BEER_ID + BEER_API_SUBPATH_INCREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO)))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void whenPATCHIsCalledToDecrementDiscountThenOKstatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(5)
//                .build();
//
//        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        beerDTO.setQuantity(beerDTO.getQuantity() + quantityDTO.getQuantity());
//
//        when(beerService.decrement(VALID_BEER_ID, quantityDTO.getQuantity())).thenReturn(beerDTO);
//
//        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_DECREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO))).andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", is(beerDTO.getName())))
//                .andExpect(jsonPath("$.brand", is(beerDTO.getBrand())))
//                .andExpect(jsonPath("$.type", is(beerDTO.getType().toString())))
//                .andExpect(jsonPath("$.quantity", is(beerDTO.getQuantity())));
//    }
//
//    @Test
//    void whenPATCHIsCalledToDEcrementLowerThanZeroThenBadRequestStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(60)
//                .build();
//
//        BeerDTO beerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        beerDTO.setQuantity(beerDTO.getQuantity() + quantityDTO.getQuantity());
//
//        when(beerService.decrement(VALID_BEER_ID, quantityDTO.getQuantity())).thenThrow(BeerStockExceededException.class);
//
//        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + VALID_BEER_ID + BEER_API_SUBPATH_DECREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO))).andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void whenPATCHIsCalledWithInvalidBeerIdToDecrementThenNotFoundStatusIsReturned() throws Exception {
//        QuantityDTO quantityDTO = QuantityDTO.builder()
//                .quantity(5)
//                .build();
//
//        when(beerService.decrement(INVALID_BEER_ID, quantityDTO.getQuantity())).thenThrow(BeerNotFoundException.class);
//        mockMvc.perform(patch(BEER_API_URL_PATH + "/" + INVALID_BEER_ID + BEER_API_SUBPATH_DECREMENT_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(quantityDTO)))
//                .andExpect(status().isNotFound());
//    }
}
