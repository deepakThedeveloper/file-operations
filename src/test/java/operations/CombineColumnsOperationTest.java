package operations;

import com.ajira.test.fileoperations.operations.CombineColumnsOperation;
import com.ajira.test.fileoperations.util.CustomProperties;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CombineColumnsOperationTest {
    private static final CombineColumnsOperation operation = new CombineColumnsOperation();

    @Test
    public void performOperation_validInputAndConfigData_returnCombinedColumnsData(){
        // Given:
        Map<String, String> originalMap = new LinkedHashMap<>();
        originalMap.put("first_name", "aa");
        originalMap.put("last_name", "bb");
        originalMap.put("age", "10");

        List<Map<String, String>> originalData = new ArrayList<>();
        originalData.add(originalMap);

        Properties properties = mock(CustomProperties.class);
        when(properties.getProperty("columnsToCombine")).thenReturn("first_name | last_name");
        when(properties.getProperty("newColumnName")).thenReturn("name");

        // When:
        List<Map<String, String>> modifiedData =
                operation.performOperation(originalData, properties);

        // Then:
        assertThat(modifiedData).isNotNull();
        assertThat(modifiedData.size()).isEqualTo(1);

        Map<String, String> modifiedMap = modifiedData.get(0);
        assertThat(modifiedMap.size()).isEqualTo(2);
        assertThat(modifiedMap.keySet()).containsExactly("age", "name");
        assertThat(modifiedMap.values()).containsExactly("10", "aa, bb");
    }
}
