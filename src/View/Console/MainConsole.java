//package View;
//
//import Controller.Controller;
//import Exceptions.MyException;
//import Model.ADTs.*;
//import Model.Expressions.*;
//import Model.PrgState;
//import Model.Statements.*;
//import Model.Types.*;
//import Model.Values.BoolValue;
//import Model.Values.IValue;
//import Model.Values.IntValue;
//import Model.Values.StringValue;
//import Repository.FileRepository;
//
//public class Interpreter {
//    public static void main(String[] args) {
//
//        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
//                                 new PrintStmt(new VarExp("v"))));
//
//        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
//                    new CompStmt(new VarDeclStmt("b", new IntType()),
//                    new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)),
//                                 new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                    new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))),
//                                 new PrintStmt(new VarExp("b"))))));
//
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
//                    new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))),
//                                 new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
//
//        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
//                    new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
//                    new CompStmt(new OpenRFileStmt(new VarExp("varf")),
//                    new CompStmt(new VarDeclStmt("varc", new IntType()),
//                    new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
//                    new CompStmt(new PrintStmt(new VarExp("varc")),
//                    new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
//                    new CompStmt(new PrintStmt(new VarExp("varc")),
//                    new CloseRFileStmt(new VarExp("varf"))))))))));
//
//        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
//                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
//                    new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
//
//        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
//                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
//                    new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
//                                 new PrintStmt(new ArithExp('+', new HeapReadExp(new HeapReadExp(new VarExp("a"))),
//                                                                      new ValueExp(new IntValue(5)))))))));
//
//        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
//                    new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))),
//                    new CompStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
//                                 new PrintStmt(new ArithExp('+', new HeapReadExp(new VarExp("v")),
//                                               new ValueExp(new IntValue(5))))))));
//
//        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(20))),
//                    new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                    new CompStmt(new HeapAllocateStmt("a", new VarExp("v")),
//                    new CompStmt(new HeapAllocateStmt("v", new ValueExp(new IntValue(30))),
//                                 new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))))))));
//
//        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
//                    new CompStmt(new WhileStmt(new RelationalExp(">", new VarExp("v"), new ValueExp(new IntValue(0))),
//                                           new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v",
//                                                   new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
//                                 new PrintStmt(new VarExp("v")))));
//
//        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                     new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
//                     new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
//                     new CompStmt(new HeapAllocateStmt("a", new ValueExp(new IntValue(22))),
//                     new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))),
//                                               new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
//                                               new CompStmt(new PrintStmt(new VarExp("v")),
//                                                            new PrintStmt(new HeapReadExp(new VarExp("a"))))))),
//                     new CompStmt(new PrintStmt(new VarExp("v")),
//                                  new PrintStmt(new HeapReadExp(new VarExp("a")))))))));
//
//        try {
//            IDict<String, IType> typeEnv1 = new Dict<>();
//            ex1.typeCheck(typeEnv1);
//            IStack<IStmt> exeStack1 = new Stack<>();
//            IDict<String, IValue> symTable1 = new Dict<>();
//            IList<IValue> out1 = new List<>();
//            FileTable fileTable1 = new FileTable();
//            Heap heapTable1 = new Heap();
//            PrgState program1 = new PrgState(exeStack1, symTable1, out1, ex1, fileTable1, heapTable1);
//            FileRepository repo1 = new FileRepository(program1, "log1.txt");
//            Controller controller1 = new Controller(repo1);
//
//            IDict<String, IType> typeEnv2 = new Dict<>();
//            ex2.typeCheck(typeEnv2);
//            IStack<IStmt> exeStack2 = new Stack<>();
//            IDict<String, IValue> symTable2 = new Dict<>();
//            IList<IValue> out2 = new List<>();
//            FileTable fileTable2 = new FileTable();
//            Heap heapTable2 = new Heap();
//            PrgState program2 = new PrgState(exeStack2, symTable2, out2, ex2, fileTable2, heapTable2);
//            FileRepository repo2 = new FileRepository(program2, "log2.txt");
//            Controller controller2 = new Controller(repo2);
//
//            IDict<String, IType> typeEnv3 = new Dict<>();
//            ex3.typeCheck(typeEnv3);
//            IStack<IStmt> exeStack3 = new Stack<>();
//            IDict<String, IValue> symTable3 = new Dict<>();
//            IList<IValue> out3 = new List<>();
//            FileTable fileTable3 = new FileTable();
//            Heap heapTable3 = new Heap();
//            PrgState program3 = new PrgState(exeStack3, symTable3, out3, ex3, fileTable3, heapTable3);
//            FileRepository repo3 = new FileRepository(program3, "log3.txt");
//            Controller controller3 = new Controller(repo3);
//
//            IDict<String, IType> typeEnv4 = new Dict<>();
//            ex4.typeCheck(typeEnv4);
//            IStack<IStmt> exeStack4 = new Stack<>();
//            IDict<String, IValue> symTable4 = new Dict<>();
//            IList<IValue> out4 = new List<>();
//            FileTable fileTable4 = new FileTable();
//            Heap heapTable4 = new Heap();
//            PrgState program4 = new PrgState(exeStack4, symTable4, out4, ex4, fileTable4, heapTable4);
//            FileRepository repo4 = new FileRepository(program4, "log4.txt");
//            Controller controller4 = new Controller(repo4);
//
//            IDict<String, IType> typeEnv5 = new Dict<>();
//            ex5.typeCheck(typeEnv5);
//            IStack<IStmt> exeStack5 = new Stack<>();
//            IDict<String, IValue> symTable5 = new Dict<>();
//            IList<IValue> out5 = new List<>();
//            FileTable fileTable5 = new FileTable();
//            Heap heapTable5 = new Heap();
//            PrgState program5 = new PrgState(exeStack5, symTable5, out5, ex5, fileTable5, heapTable5);
//            FileRepository repo5 = new FileRepository(program5, "log5.txt");
//            Controller controller5 = new Controller(repo5);
//
//            IDict<String, IType> typeEnv6 = new Dict<>();
//            ex6.typeCheck(typeEnv6);
//            IStack<IStmt> exeStack6 = new Stack<>();
//            IDict<String, IValue> symTable6 = new Dict<>();
//            IList<IValue> out6 = new List<>();
//            FileTable fileTable6 = new FileTable();
//            Heap heapTable6 = new Heap();
//            PrgState program6 = new PrgState(exeStack6, symTable6, out6, ex6, fileTable6, heapTable6);
//            FileRepository repo6 = new FileRepository(program6, "log6.txt");
//            Controller controller6 = new Controller(repo6);
//
//            IDict<String, IType> typeEnv7 = new Dict<>();
//            ex7.typeCheck(typeEnv7);
//            IStack<IStmt> exeStack7 = new Stack<>();
//            IDict<String, IValue> symTable7 = new Dict<>();
//            IList<IValue> out7 = new List<>();
//            FileTable fileTable7 = new FileTable();
//            Heap heapTable7 = new Heap();
//            PrgState program7 = new PrgState(exeStack7, symTable7, out7, ex7, fileTable7, heapTable7);
//            FileRepository repo7 = new FileRepository(program7, "log7.txt");
//            Controller controller7 = new Controller(repo7);
//
//            IDict<String, IType> typeEnv8 = new Dict<>();
//            ex8.typeCheck(typeEnv8);
//            IStack<IStmt> exeStack8 = new Stack<>();
//            IDict<String, IValue> symTable8 = new Dict<>();
//            IList<IValue> out8 = new List<>();
//            FileTable fileTable8 = new FileTable();
//            Heap heapTable8 = new Heap();
//            PrgState program8 = new PrgState(exeStack8, symTable8, out8, ex8, fileTable8, heapTable8);
//            FileRepository repo8 = new FileRepository(program8, "log8.txt");
//            Controller controller8 = new Controller(repo8);
//
//            IDict<String, IType> typeEnv9 = new Dict<>();
//            ex9.typeCheck(typeEnv9);
//            IStack<IStmt> exeStack9 = new Stack<>();
//            IDict<String, IValue> symTable9 = new Dict<>();
//            IList<IValue> out9 = new List<>();
//            FileTable fileTable9 = new FileTable();
//            Heap heapTable9 = new Heap();
//            PrgState program9 = new PrgState(exeStack9, symTable9, out9, ex9, fileTable9, heapTable9);
//            FileRepository repo9 = new FileRepository(program9, "log9.txt");
//            Controller controller9 = new Controller(repo9);
//
//            IDict<String, IType> typeEnv10 = new Dict<>();
//            ex10.typeCheck(typeEnv10);
//            IStack<IStmt> exeStack10 = new Stack<>();
//            IDict<String, IValue> symTable10 = new Dict<>();
//            IList<IValue> out10 = new List<>();
//            FileTable fileTable10 = new FileTable();
//            Heap heapTable10 = new Heap();
//            PrgState program10 = new PrgState(exeStack10, symTable10, out10, ex10, fileTable10, heapTable10);
//            FileRepository repo10 = new FileRepository(program10, "log10.txt");
//            Controller controller10 = new Controller(repo10);
//
//            TextMenu menu = new TextMenu();
//            menu.addCommand(new ExitCommand("0", "exit"));
//            menu.addCommand(new RunExample("1", ex1.toString(), controller1));
//            menu.addCommand(new RunExample("2", ex2.toString(), controller2));
//            menu.addCommand(new RunExample("3", ex3.toString(), controller3));
//            menu.addCommand(new RunExample("4", ex4.toString(), controller4));
//            menu.addCommand(new RunExample("5", ex5.toString(), controller5));
//            menu.addCommand(new RunExample("6", ex6.toString(), controller6));
//            menu.addCommand(new RunExample("7", ex7.toString(), controller7));
//            menu.addCommand(new RunExample("8", ex8.toString(), controller8));
//            menu.addCommand(new RunExample("9", ex9.toString(), controller9));
//            menu.addCommand(new RunExample("10", ex10.toString(), controller10));
//            menu.show();
//        } catch (MyException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//}
