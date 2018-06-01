package com.shopapp.ui.category.router

import com.nhaarman.mockito_kotlin.mock
import com.shopapp.TestShopApplication
import com.shopapp.gateway.entity.Category
import com.shopapp.ui.category.CategoryActivity
import com.shopapp.ui.category.CategoryListActivity
import com.shopapp.ui.category.CategoryListFragment
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, application = TestShopApplication::class)
class CategoryListRouterTest {

    private lateinit var fragment: CategoryListFragment
    private lateinit var router: CategoryListRouter

    @Before
    fun setUp() {
        fragment = CategoryListFragment()
        SupportFragmentTestUtil.startFragment(fragment)
        router = CategoryListRouter()
    }

    @Test
    fun shouldStartCategoryActivity() {
        val category: Category = mock()
        router.showCategory(fragment.context, category)

        val startedIntent = Shadows.shadowOf(fragment.activity).nextStartedActivity
        val shadowIntent = Shadows.shadowOf(startedIntent)
        assertEquals(category, startedIntent.extras.getParcelable(CategoryActivity.EXTRA_CATEGORY))
        assertEquals(CategoryActivity::class.java, shadowIntent.intentClass)
    }

    @Test
    fun shouldStartCategoryListActivity() {
        val category: Category = mock()
        val isGrid = false
        router.showCategoryList(fragment.context, category, isGrid)

        val startedIntent = Shadows.shadowOf(fragment.activity).nextStartedActivity
        val shadowIntent = Shadows.shadowOf(startedIntent)
        assertEquals(category, startedIntent.extras.getParcelable(CategoryListActivity.PARENT_CATEGORY))
        assertFalse(startedIntent.extras.getBoolean(CategoryListActivity.IS_GRID_MODE))
        assertEquals(CategoryListActivity::class.java, shadowIntent.intentClass)
    }
}