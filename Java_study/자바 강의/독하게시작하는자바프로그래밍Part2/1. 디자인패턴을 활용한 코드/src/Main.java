import linkedList.MyIterator;
import linkedList.MyList;
import linkedList.MyNode;
import java.util.Scanner;

class UserData extends MyNode {
    UserData(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    String name;
    String phone;

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public void printNode() {
        System.out.println("Name: " + name + " Phone: " + phone);
    }

    @Override
    public MyNode getNode() {
        return new UserData(name, phone);
    }
}

class UserInterface {
    MyList list;
    UserInterface(MyList list) {
        this.list = list;
    }

    public void addUser() {
        Scanner s = new Scanner(System.in);
        System.out.print("Name: ");
        String name = s.nextLine();
        System.out.print("Phone: ");
        String phone = s.nextLine();

        list.addNewNode(new UserData(name, phone));
    }

    public void searchUser() {
        Scanner s = new Scanner(System.in);
        System.out.print("Name: ");
        String name = s.nextLine();

        MyNode user = list.findNode(name);
        if (user != null) {
            user.printNode();
        }
        else {
            System.out.println("No such user");
        }
    }

    public void printAll() {
        MyIterator it = list.makeIterator();
        while (it.getCurrent() != null) {
            it.getCurrent().printNode();
            it.moveNext();
        }
    }

    public void removeUser() {
        Scanner s = new Scanner(System.in);
        System.out.print("Name: ");
        String name = s.nextLine();

        if (list.removeNode(name)) {
            System.out.println("User removed");
        }
        else {
            System.out.println("No such user");
        }
    }

    public int printUi() {
        System.out.println("[1] Add\t[2] Search\t[3] Print all\t[4] Remove\t[0] Exit");
        Scanner s = new Scanner(System.in);
        System.out.print(":");
        int input  = s.nextInt();
        return input;
    }

    public void run() {
        int menu = 0;
        while((menu = printUi()) != 0) {
            switch (menu) {
                case 1:
                    addUser();
                    break;
                case 2:
                    searchUser();
                    break;
                case 3:
                    printAll();
                    break;
                case 4:
                    removeUser();
                    break;
            }
        }
    }
}

class MyListEx extends MyList {
    public MyListEx(MyNode dummyHead) {
        super(dummyHead);
    }

    @Override
    public void onRemoveNode() {
        System.out.println("노드가 삭제되었습니다.");
    }
}

public class Main {
    public static void main(String[] args) {
        MyList db = new MyListEx(new UserData("Dummy", "Dummy"));
        UserInterface ui = new UserInterface(db);
        ui.run();
    }
}