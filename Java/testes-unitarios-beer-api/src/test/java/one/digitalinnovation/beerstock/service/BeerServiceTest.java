package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.exception.BeerStockExceededException;
import one.digitalinnovation.beerstock.mapper.BeerMapper;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) //para criar "objetos dublê" para simular os comportamentos que desejamos
public class BeerServiceTest {

    private static final long INVALID_BEER_ID = 2L;

    @Mock
    private BeerRepository beerRepository;

    //auxilia na conversão do objeto Beer para model ou DTO
    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks //injeção do mock (dublê) do BeerRepository
    private BeerService beerService;

    //validação do método para criação de uma cerveja com sucesso
    @Test
    void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException{
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); //cerveja de exemplo hardcoded
        Beer expectedSavedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        //mockando o comportamento de verificar que a cerveja não existe e retornando a cerveja salva
        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
        when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

        //then
        //execução de fato da criação de uma cerveja
        BeerDTO createdBeerDTO = beerService.createBeer(expectedBeerDTO);

        //validação com o Hamcrest (retornado/esperado)
        assertThat(createdBeerDTO.getId(), is(equalTo(expectedBeerDTO.getId())));
        assertThat(createdBeerDTO.getName(), is(equalTo(expectedBeerDTO.getName())));
        assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedBeerDTO.getQuantity())));

        //com o Hamcrest é possível fazer outras validações além de equalTo
        assertThat(createdBeerDTO.getQuantity(), is(greaterThan(2))); //qtd hardcoded é 10

        //validação com o JUnit (esperado/retornado)
        assertEquals(expectedBeerDTO.getId(), createdBeerDTO.getId());
        assertEquals(expectedBeerDTO.getName(), createdBeerDTO.getName()); 
    }
    
    //validação do método para criação de uma cerveja, gerando exceção que a cerveja já existe
    @Test
    void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown(){
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); //cerveja de exemplo hardcoded
        Beer duplicatedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        //mockando o caso de exceção
        when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

        //then
        //JUnit - valida se a exceção foi gerada pela função createBeer
        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeerDTO));

    }

    //validação de sucesso do método para consulta ao banco pelo nome da cerveja
    @Test
    void whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundException {
        //given
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); //cerveja de exemplo hardcoded
        Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        //when
        //mockando sucesso em encontrar uma cerveja pelo nome
        when(beerRepository.findByName(expectedFoundBeer.getName())).thenReturn(Optional.of(expectedFoundBeer));

        //then
        BeerDTO foundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());
        
        //validando com Hamcrest
        assertThat(foundBeerDTO, is(equalTo(expectedFoundBeerDTO)));
    }    

    //validação do erro do método para consulta ao banco pelo nome da cerveja, quando o nome não é encontrado
    @Test
    void whenNotRegisteredBeerNameIsGivenThenThrowAnException(){
        //given
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); //cerveja de exemplo hardcoded
        
        //when
        //mockando o retorno vazio, que irá gerar a exceção
        when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());

        //then
        //a chamada ao método já irá gerar a exceção
        assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName()));
        
    } 

    //validação de sucesso em retornar a lista com todas as cervejas
    @Test
    void whenListBeerIsCalledThenReturnAListOfBeers(){
        //given
        //iremos testar com somente uma cerveja (hardcoded) inserida no banco
        BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); 
        Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

        //when
        when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));
 
        //then
        List<BeerDTO> foundListBeerDTO = beerService.listAll();

        //validação com o Hamcrest
        assertThat(foundListBeerDTO, is(not(empty()))); //não retornou lista vazia
        assertThat(foundListBeerDTO.get(0), is(equalTo(expectedFoundBeerDTO))); //lembrando que só temos um elemento neste test
    }

    //validação de retorno de lista vazia quando nenhuma cerveja foi cadastrada
    @Test
    void whenListBeerIsCalledThenReturnAnEmptyListOfBeers(){
        //when
        when(beerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);
 
        //then
        List<BeerDTO> foundListBeerDTO = beerService.listAll();

        //validação com o Hamcrest
        assertThat(foundListBeerDTO, is(empty()));
    }

    //validação de sucesso ao remover a cerveja pelo ID
    @Test
    void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws BeerNotFoundException{
        //given
        BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); //cerveja de exemplo hardcoded
        Beer expectedDeletedBeer = beerMapper.toModel(expectedDeletedBeerDTO);

        //necessário mockar se encontra a cerveja pelo ID e depois se a remoção ocorreu
        //when
        when(beerRepository.findById(expectedDeletedBeerDTO.getId())).thenReturn(Optional.of(expectedDeletedBeer));
        doNothing().when(beerRepository).deleteById(expectedDeletedBeerDTO.getId()); //o método de deleção retorna void

        //then
        beerService.deleteById(expectedDeletedBeerDTO.getId());

        //neste caso, não é possível fazer o assert, pois a função não retorna nada, 
        //então será usado o próprio mockito para verificar se os métodos foram chamados uma vez cada um
        verify(beerRepository, times(1)).findById(expectedDeletedBeerDTO.getId());
        verify(beerRepository, times(1)).deleteById(expectedDeletedBeerDTO.getId());
    }
    
    @Test
    void whenExclusionIsCalledWithInValidIdThenAnExceptionShouldBeThrown(){
        //when
        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

        //then
        assertThrows(BeerNotFoundException.class, () -> beerService.deleteById(INVALID_BEER_ID));
    }

    @Test
    void whenIncrementIsCalledThenIncrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {
        //given
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        //when
        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

        int quantityToIncrement = 10;
        int expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;

        // then
        BeerDTO incrementedBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

        assertThat(expectedQuantityAfterIncrement, equalTo(incrementedBeerDTO.getQuantity()));
        assertThat(expectedQuantityAfterIncrement, lessThan(expectedBeerDTO.getMax()));
    }

    @Test
    void whenIncrementIsGreatherThanMaxThenThrowException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        int quantityToIncrement = 80; //é maior que o máximo permitido (50)
        assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
    }

    @Test
    void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

        int quantityToIncrement = 45; //neste caso, o máximo permitido irá ultrapassar quando somado ao que já existe em estoque
        assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
    }

// Comentando os códigos que já vieram com o projeto baixado no desafio
    // @Test
    // void whenBeerInformedThenItShouldBeCreated() throws BeerAlreadyRegisteredException {
    //     // given
    //     BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO(); 
    //     Beer expectedSavedBeer = beerMapper.toModel(expectedBeerDTO);

    //     // when
    //     when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.empty());
    //     when(beerRepository.save(expectedSavedBeer)).thenReturn(expectedSavedBeer);

    //     //then
    //     BeerDTO createdBeerDTO = beerService.createBeer(expectedBeerDTO);

    //     assertThat(createdBeerDTO.getId(), is(equalTo(expectedBeerDTO.getId())));
    //     assertThat(createdBeerDTO.getName(), is(equalTo(expectedBeerDTO.getName())));
    //     assertThat(createdBeerDTO.getQuantity(), is(equalTo(expectedBeerDTO.getQuantity())));
    // }

    // @Test
    // void whenAlreadyRegisteredBeerInformedThenAnExceptionShouldBeThrown() {
    //     // given
    //     BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     Beer duplicatedBeer = beerMapper.toModel(expectedBeerDTO);

    //     // when
    //     when(beerRepository.findByName(expectedBeerDTO.getName())).thenReturn(Optional.of(duplicatedBeer));

    //     // then
    //     assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(expectedBeerDTO));
    // }

    // @Test
    // void whenValidBeerNameIsGivenThenReturnABeer() throws BeerNotFoundException {
    //     // given
    //     BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

    //     // when
    //     when(beerRepository.findByName(expectedFoundBeer.getName())).thenReturn(Optional.of(expectedFoundBeer));

    //     // then
    //     BeerDTO foundBeerDTO = beerService.findByName(expectedFoundBeerDTO.getName());

    //     assertThat(foundBeerDTO, is(equalTo(expectedFoundBeerDTO)));
    // }

    // @Test
    // void whenNotRegisteredBeerNameIsGivenThenThrowAnException() {
    //     // given
    //     BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();

    //     // when
    //     when(beerRepository.findByName(expectedFoundBeerDTO.getName())).thenReturn(Optional.empty());

    //     // then
    //     assertThrows(BeerNotFoundException.class, () -> beerService.findByName(expectedFoundBeerDTO.getName()));
    // }

    // @Test
    // void whenListBeerIsCalledThenReturnAListOfBeers() {
    //     // given
    //     BeerDTO expectedFoundBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     Beer expectedFoundBeer = beerMapper.toModel(expectedFoundBeerDTO);

    //     //when
    //     when(beerRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundBeer));

    //     //then
    //     List<BeerDTO> foundListBeersDTO = beerService.listAll();

    //     assertThat(foundListBeersDTO, is(not(empty())));
    //     assertThat(foundListBeersDTO.get(0), is(equalTo(expectedFoundBeerDTO)));
    // }

    // @Test
    // void whenListBeerIsCalledThenReturnAnEmptyListOfBeers() {
    //     //when
    //     when(beerRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

    //     //then
    //     List<BeerDTO> foundListBeersDTO = beerService.listAll();

    //     assertThat(foundListBeersDTO, is(empty()));
    // }

    // @Test
    // void whenExclusionIsCalledWithValidIdThenABeerShouldBeDeleted() throws BeerNotFoundException{
    //     // given
    //     BeerDTO expectedDeletedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     Beer expectedDeletedBeer = beerMapper.toModel(expectedDeletedBeerDTO);

    //     // when
    //     when(beerRepository.findById(expectedDeletedBeerDTO.getId())).thenReturn(Optional.of(expectedDeletedBeer));
    //     doNothing().when(beerRepository).deleteById(expectedDeletedBeerDTO.getId());

    //     // then
    //     beerService.deleteById(expectedDeletedBeerDTO.getId());

    //     verify(beerRepository, times(1)).findById(expectedDeletedBeerDTO.getId());
    //     verify(beerRepository, times(1)).deleteById(expectedDeletedBeerDTO.getId());
    // }

    // @Test
    // void whenIncrementIsCalledThenIncrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {
    //     //given
    //     BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

    //     //when
    //     when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
    //     when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);

    //     int quantityToIncrement = 10;
    //     int expectedQuantityAfterIncrement = expectedBeerDTO.getQuantity() + quantityToIncrement;

    //     // then
    //     BeerDTO incrementedBeerDTO = beerService.increment(expectedBeerDTO.getId(), quantityToIncrement);

    //     assertThat(expectedQuantityAfterIncrement, equalTo(incrementedBeerDTO.getQuantity()));
    //     assertThat(expectedQuantityAfterIncrement, lessThan(expectedBeerDTO.getMax()));
    // }

    // @Test
    // void whenIncrementIsGreatherThanMaxThenThrowException() {
    //     BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

    //     when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

    //     int quantityToIncrement = 80;
    //     assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
    // }

    // @Test
    // void whenIncrementAfterSumIsGreatherThanMaxThenThrowException() {
    //     BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
    //     Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);

    //     when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));

    //     int quantityToIncrement = 45;
    //     assertThrows(BeerStockExceededException.class, () -> beerService.increment(expectedBeerDTO.getId(), quantityToIncrement));
    // }

    // @Test
    // void whenIncrementIsCalledWithInvalidIdThenThrowException() {
    //     int quantityToIncrement = 10;

    //     when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());

    //     assertThrows(BeerNotFoundException.class, () -> beerService.increment(INVALID_BEER_ID, quantityToIncrement));
    // }
//
//    @Test
//    void whenDecrementIsCalledThenDecrementBeerStock() throws BeerNotFoundException, BeerStockExceededException {
//        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
//
//        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
//        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);
//
//        int quantityToDecrement = 5;
//        int expectedQuantityAfterDecrement = expectedBeerDTO.getQuantity() - quantityToDecrement;
//        BeerDTO incrementedBeerDTO = beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement);
//
//        assertThat(expectedQuantityAfterDecrement, equalTo(incrementedBeerDTO.getQuantity()));
//        assertThat(expectedQuantityAfterDecrement, greaterThan(0));
//    }
//
//    @Test
//    void whenDecrementIsCalledToEmptyStockThenEmptyBeerStock() throws BeerNotFoundException, BeerStockExceededException {
//        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
//
//        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
//        when(beerRepository.save(expectedBeer)).thenReturn(expectedBeer);
//
//        int quantityToDecrement = 10;
//        int expectedQuantityAfterDecrement = expectedBeerDTO.getQuantity() - quantityToDecrement;
//        BeerDTO incrementedBeerDTO = beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement);
//
//        assertThat(expectedQuantityAfterDecrement, equalTo(0));
//        assertThat(expectedQuantityAfterDecrement, equalTo(incrementedBeerDTO.getQuantity()));
//    }
//
//    @Test
//    void whenDecrementIsLowerThanZeroThenThrowException() {
//        BeerDTO expectedBeerDTO = BeerDTOBuilder.builder().build().toBeerDTO();
//        Beer expectedBeer = beerMapper.toModel(expectedBeerDTO);
//
//        when(beerRepository.findById(expectedBeerDTO.getId())).thenReturn(Optional.of(expectedBeer));
//
//        int quantityToDecrement = 80;
//        assertThrows(BeerStockExceededException.class, () -> beerService.decrement(expectedBeerDTO.getId(), quantityToDecrement));
//    }
//
//    @Test
//    void whenDecrementIsCalledWithInvalidIdThenThrowException() {
//        int quantityToDecrement = 10;
//
//        when(beerRepository.findById(INVALID_BEER_ID)).thenReturn(Optional.empty());
//
//        assertThrows(BeerNotFoundException.class, () -> beerService.decrement(INVALID_BEER_ID, quantityToDecrement));
//    }
}
