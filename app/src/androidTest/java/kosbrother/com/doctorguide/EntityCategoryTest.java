package kosbrother.com.doctorguide;


import android.test.InstrumentationTestCase;

import java.util.ArrayList;

import kosbrother.com.doctorguide.entity.Category;

public class EntityCategoryTest extends InstrumentationTestCase{

    public void testCategoryFields() throws Exception {
        ArrayList<Category> categories = Category.getCategories();
        for(Category category: categories){
            assertNotNull(category.id);
            assertNotNull(category.name);
            assertNotNull(category.intro);
        }
    }

    public void testCategorySize() throws Exception {
        ArrayList<Category> categories = Category.getCategories();
        assertEquals(24,categories.size());
    }
}