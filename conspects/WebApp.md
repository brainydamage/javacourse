
```
 ______       _____________        ____________________       ____
|      |     |             |      |                    |     |    |
| User | --> | HTTP Server | -->  | Application Server | --> | DB |
|______|     |_____________|      |____________________|     |____|
```

**HTTP (Web) сервер (Nginx, Apache HTTPD)**
1) первым принимает запросы от пользователей;
2) отдает и управляет статическим контентом - картинки, видео, стили, странички;
3) может перенаправить запрос на Application Server;
4) может играть роль Load Balancer'а;
5) может кешировать ответы, чтобы каждый раз на один запрос не дергать Java приложение;
6) поддерживает только Servlets и JSP;
7) поддерживает SSL и HTTPS.

**Application Server**
1) предоставляет клиенту "доступ" к бизнес-логике используя различные протоколы, включая HTTP;
2) отвечает да динамический контект;
3) поддерживает многопоточность;
4) могут поддерживать SSL, кеширование, маршрутизация и управлять статическим контентом, но не так гибко и быстро как HTTP Server.
5) бывают трех типов:
    * J2EE совместимые - GlassFish, WildFly, WebLogic, WebSphere. Полностью реализуют спецификацию J2EE, включая Servlets API, EJB, Distributed Transactions, JMS.
    * Контейнеры сервлетов - Tomcat, Jetty, Undertow. Реализуют спецификацию J2EE частично, самое главное - Servlets API.
    * Non-J2EE - Undertow, Play Framework. Реализуют работу с HTTP не через Servlets API, а через собственные интерфейсы.

**Сервлет**

Сервлет - это Java класс, который получает запросы, обрабатывает их и возвращает ответ. За отправку запроса к определенному сервлету отвечает Application Server (контейнер сервлетов). Application Server отвечает за получение и обработку самого HTTP запроса на уровне сети, тогда как сам сервлет реализует бизнес-логику. То, что именно должен делать контейнер сервлетов, определяется спецификацией Java Servlet Specification. Сервлет создается и умирает вместе с контейнером.  
В самом сервлете описывается поведение в переопределенных методах: init(), doGet(), doPost(), destroy() и тд.


Клиент отправляет HTTP запрос на HTTP Server -->  
HTTP Server перенаправляет запрос на Application Server ->>  
Application Server конвертирует запрос в объект, с которым умеет работать сервлет ->>  
Application Server создает новый поток для каждого запроса ->>  
Запрос обрабатывается в определенном методе (doGet(), doPost()) ->>  
Сервлет возвращает объект на Application Server ->>  
Application Server конвертирует объект в HTTP респонс ->>  
Application Server возвращает респонс клиенту.

**web.xml**

Дескриптор web.xml нужен для того, чтобы контейнер сервлетов знал, на какой именно сервлет нужно направить запрос: <servlet-mapping> указывается url паттерн и имя сервлета.  

**Filter**

Servlet Filter это подлключаемый компонент, который может перехватывать HTTP запросы и обрабатывать их каким-то образом перед отправкой на сервлет, а так же обрабатывать ответы перед отправкой на контейнер:  
* аутентификация
* формирование тела запроса или хедера
* сжатие данные
* изменение кодировки и тд  

Файлы сервлетов настраиваются и подключаются в том же дескрипторе web.xml. Можно иметь несколько фильтров для одного ресурса, можно создавать цепочки фильтров ([Chain of Responsibility](https://www.journaldev.com/1617/chain-of-responsibility-design-pattern-in-java)). В классе фильтра предоставляется реализация для переопределенных методов: init(), doFilter(), destroy(). Порядок вызова фильтров определяется исходя из конфигурации дескриптора, при этом сначала обрабатываются url паттерны сервлетов, а потом их имена.


**Listener**

[Servlet Listener guide](https://www.journaldev.com/1945/servletcontextlistener-servlet-listener-example)

Это классы, которые могут ловить ивенты: сессия создана или удалена, добавлены или удалены аттрибуты сессии:  
```
AsyncListener  
ServletContextListener  
ServletContextAttributeListener  
ServletRequestListener  
ServletRequestAttributeListener  
HttpSessionListener  
HttpSessionAttributeListener  
HttpSessionBindingListener  
HttpSessionActivationListener
```

События:
* события уровня приложения (контекста) - ServletContextListener, ServletContextAttributeListener, etc;
* события уровня сессии - HttpSessionListener, HttpSessionAttributeListener, etc..  

На каждом из этих уровней можно работать с двумя типами событий:
* изменения жизненного цикла - ServletContextListener, HttpSessionListener, etc.;
* изменений аттрибутов - HttpSessionListener, HttpSessionAttributeListener, etc..



**Blocking Queue**

[Обзор java.util.concurrent.*](https://habr.com/ru/company/luxoft/blog/157273/)

BlockingQueues — блокирующие очереди с поддержкой многопоточности. Релизуют взаимодействие "producer- consumer", методы потокобезопасны (синхронизированы) => и писать в нее, и читать из неё можно в несколько потоков.  Используются, когда нужно «притормозить» потоки «Producer» или «Consumer», если не выполнены какие-либо условия, например, очередь пуста или перепонена, или же нет свободного «Consumer»'a.

Интерфейсы:
* java.util.concurrent.BlockingQueue
* java.util.concurrent.BlockingDeque
* java.util.concurrent.TransferQueue

Реализации java.util.concurrent.BlockingQueue:
* ArrayBlockingQueue — очередь FIFO, реализующая классический кольцевой буфер (ArrayList);
* LinkedBlockingQueue — односторонняя очередь FIFO на связанных узлах (LinkedList);
* PriorityBlockingQueue — реализация очереди с приоритетом на основе интерфейса PriorityQueue.
* SynchronousQueue — блокирующую очередь без емкости (операция добавления одного потока находится в ожидании соответствующей операции удаления в другом потоке);
* DelayQueue — неограниченная блокирующая очередь, реализующая интерфейс Delayed;
* LinkedTransferQueue — реализация очереди на основе интерфейса TransferQueue;
* LinkedBlockingDeque — двунаправленная очередь на связанных узлах;

Методы:
* take() - берет след.элемент или блокирует выполнение до появления элемента (когда очередь перестанет быть пустой)
* poll (long timeout, TimeUnit unit) - берет след.элемент или блокирует выполнение на время unit, дожидаясь появления элемента
* put(E e) - кладет к очередь, если есть место, или блокирует выполнение до освобождения места в очереди
* offer (E e) - кладет в очередь немедленно, возвращает true или false
* offer (E e, long timeout, TimeUnit unit) - кладет в очередь, если есть место, или блокирует выполнение на время unit, дожидаясь места

**Thread.State**
  
NEW - только что создан  
RUNNABLE - уже выполняется (то есть выполняется метод run()) или готов начать выполняться (run() еще не вызван)
BLOCKED - ждет монитор, чтобы зайти в synchronized блок/метод
WAITING - ждет бесконечно, когда другой поток выполнит определенное действие, например, notify(), попадает в это состояние после вызова:
* Object.wait без таймаута
* Thread.join без таймаута
* LockSupport.park 
 
TIMED_WAITING - ждет указанное время, когда другой поток выполнит определенное действие, результат вызова:
* Thread.sleep
* Object.wait с таймаутом
* Thread.join с таймаутом
* LockSupport.parkNanos
* LockSupport.parkUntil

TERMINATED - мертвый поток

Можно попросить поток остановить работу вызовом interrupt(), однако это не гарантирует, что поток будет остановлен, и поведение зависит от конкретной реализации метода run(): если нет проверки флага Thread.currentThread().isInterrupted(), то ничего и не произойдет.


**Thread Pools**

[IBM - Пулы потоков и очередь действий](https://www.ibm.com/developerworks/ru/library/j-jtp0730/index.html)
Создание потока - дорогая операция, которая требует как много времени, так и много системных ресурсов. Если нужно выполнять много мелких задач, то накладных расходов будет больше, чем полезных расходов. Более того, создание слишком большого количества поток может привести к нехватке памяти, то есть нужен какой-то механизм ограничения количества одновременных запросов. С частыми, но короткими запросами связана работа веб-серверов, серверов баз данных и тд.
Для этого существует пул потоков, где уже существует ограниченное количество потоков, а внутри пула реализована блокирущая очередь - как только поток освобождается, он берет в работу новую задачу. А когда задач нет - поток заблокирован в ожидании задач. То есть потоки в пуле переиспользуются, что позволяет сэкономить на создании новых потоков под каждую задачу.  


**Executors**

Executors - самая большая часть пакета java.util.concurrent. Внутри - интерфейсы для запуска асинхронных задач с возможностью получения результата через Future и Callable интерфейсы, а также сервисы и фабрики для создания пулов потоков: ThreadPoolExecutor, ScheduledPoolExecutor, ForkJoinPool.  

Executor (interface) -->  
ExecutorService (interface) -->  
(ScheduledThreadPoolExecutor (interface) -->  )
AbstractExecutorService, ForkJoinPool, ScheduledThreadPoolExecutor, ThreadPoolExecutor (classes)

Executor - базовый интерфейс для классов, реализующих запуск Runnable задач, обеспечивает развязывание процессов добавления задачи и ее запуска, в наличии только один метод execute(Runnable command).

[ExecutorService](https://habr.com/en/post/116363/) - интерфейс для создания пула потоков и запуска Runnable и Callable задач:
* execute(Runnable task) - метод из Executor, ничего не возвращает
* submit(Runnable/Callable task) - принимает задачу и возвращае Future, через который можно получить результат. 
* invokeAll(сollection of Runnable/Callable tasks) - вызывающий поток блокируется до выполнения всех задач в списке или по таймауту
* invokeAny(сollection of Runnable/Callable tasks) - вызывающий поток блокируется до выполнения любой из переданных задач
* shutdown() - сервис перестает принимать задачи, возвращая RejectedExecutionException  

ScheduledExecutorService - интерфейс, дополненный возможностью запускать отложенные задачи.

AbstractExecutorService - абстрактный класс для построения ExecutorService. Содержит базовую имплементацию submit, invokeAll, invokeAny. Является родителем для ForkJoinPool, ScheduledThreadPoolExecutor, ThreadPoolExecutor.

java.util.concurrent.Executors (extends Object) - класс-фабрика для создания ThreadPoolExecutor и ScheduledThreadPoolExecutor, а также здесь есть набор методов-адаптеров Runnable->Callable и других->Callable.

java.util.concurrent.ThreadPoolExecutor - класс для запуска асинхронных задач в пуле потоков. Пул создается через фабрику Executors.

