package br.com.gabriellferreira.recipelist.presentation.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.gabriellferreira.recipelist.domain.model.NetworkState
import br.com.gabriellferreira.recipelist.domain.usecase.RecipeUseCase
import br.com.gabriellferreira.recipelist.presentation.di.AppApplication
import br.com.gabriellferreira.recipelist.presentation.di.DaggerTestAppComponent
import br.com.gabriellferreira.recipelist.presentation.di.TestAppModule
import br.com.gabriellferreira.recipelist.presentation.di.TestRepositoryModule
import br.com.gabriellferreira.recipelist.presentation.model.RecipeItem
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Scheduler
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import javax.inject.Inject

class RecipeListViewModelTest {

    @Rule
    @JvmField
    var instantTaskRule: InstantTaskExecutorRule? = InstantTaskExecutorRule()

    @Inject
    lateinit var scheduler: Scheduler
    @Mock
    lateinit var useCase: RecipeUseCase
    lateinit var viewModel: RecipeListViewModel

    @Before
    fun setup() {
        val component = DaggerTestAppComponent.builder()
            .appModule(TestAppModule(AppApplication()))
            .repositoryModule(TestRepositoryModule())
            .build()
        component.inject(this)
        MockitoAnnotations.initMocks(this)

        viewModel = RecipeListViewModel(useCase, scheduler)
    }

    @Test
    fun `should return InProgress`() {
        // given
        whenever(useCase.fetchRecipeList()).thenReturn(
            Single.never()
        )

        // when
        viewModel.fetchRecipeList()

        // then
        assertThat(viewModel.itemList.value).isInstanceOf(NetworkState.InProgress::class.java)
    }

    @Test
    fun `should return Error state`() {
        // given
        whenever(useCase.fetchRecipeList()).thenReturn(
            Single.error(Exception())
        )

        // when
        viewModel.fetchRecipeList()

        // then
        assertThat(viewModel.itemList.value).isInstanceOf(NetworkState.Error::class.java)
    }

    @Test
    fun `should return Loaded state`() {
        // given
        whenever(useCase.fetchRecipeList()).thenReturn(
            Single.just(
                listOf(
                    RecipeItem(
                        id = "1234",
                        title = "title",
                        description = "description",
                        caloriesString = "caloriesString",
                        chefName = "chefName",
                        chefNameString = "chefNameString",
                        tags = listOf(),
                        thumbnailUrl = "thumbnailUrl",
                        tagsString = "tagsString"
                    )
                )
            )
        )

        // when
        viewModel.fetchRecipeList()

        // then
        assertThat(viewModel.itemList.value).isInstanceOf(NetworkState.Loaded::class.java)
        assertThat((viewModel.itemList.value as NetworkState.Loaded).result.first().id).isEqualTo("1234")
    }
}