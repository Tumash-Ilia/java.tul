package comparingstudents.mycomparing;

import comparingstudents.Student;

public class PorovnavacStudentByLastname implements ComparatorInterface {

        @Override
        public boolean bigger(Object o1, Object o2) {
            return ((Student)o1).getLastName().compareTo(((Student)o2).getLastName()) > 0;
        }

    }

