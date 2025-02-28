package school.redrover.testdata;

import school.redrover.models.view.ListView;

public class ListViewTestData {

    public static ListView getListView(String projectName) {
        return ListView.builder()
                .name("")
                .filterExecutors(false)
                .filterQueue(false)
                .properties("")
                .jobNames(ListView.JobNames.builder()
                        .jobName(projectName)
                        .build())
                .jobFilters("")
                .columns(ListView.Columns.builder()
                        .statusColumn("")
                        .weatherColumn("")
                        .jobColumn("")
                        .lastSuccessColumn("")
                        .lastFailureColumn("")
                        .lastDurationColumn("")
                        .buildButtonColumn("")
                        .build())
                .recurse(false)
                .build();
    }
}