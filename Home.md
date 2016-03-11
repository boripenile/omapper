# Introduction #

Omapper is a simple library which provides utilities to transform java beans from one design layer to another.
Say for example ,
you have a legacy application which has some java beans like Customer, Account etc.
And while adding another access layer like web services, it would again have some bean with same structure and data as the backend application.
So , as a result we have two similar classes.
Now , while communicating between different layers , we write a error prone transformation logic which comprises of multiple chained get and set operations.
For Example:
There is a business layer which has a bean called Bean1:
```
   public class Bean1 {

	/** The name. */
	private String name;

	/** The address. */
	private String address;
	
	/** The age. */
	private int age;
	
	/** The emp_id. */
	private Integer emp_id;
}

```

And similarly web service layer above that also exposes a bean called
Bean2 with exactly same structure as BS one:

```
   public class Bean2 {

	/** The name. */
	private String name;
	
	/** The address. */
	private String address;
	
	/** The age. */
	private int age;
	
	/** The emp_id. */
	private Integer emp_id;        
}


```

Earlier you would write some transformation method like :
```
 transformBean1ToBean2(Bean1 bean1, Bean2 bean2)
	{
		bean2.setAddress(bean1.getAddress());
		bean2.setAge(bean1.getAge());
		bean2.setEmp_id(bean1.getEmp_id());
		bean2.setName(bean1.getName());
	}

```

So, this approach has a few drawbacks:

=> As the number of properties increase the get-set clutter increase and sometimes leads to missing mappings.

=> Anytime a new property is added or removed both bean definition and
transformation logic needs to revisited.

=> Matters get worse when the names of the properties in the two bean classes have different names but same information, the get-set code gets more error prone.

=> What if the target bean needs to collate information from more than one bean? The get-set block tends to be a bit messed up.



---

# Usage: #

The library Omapper tries to solve the above problems by providing annotation based way to specify the bean mappings.
There are more powerful frameworks for this purpose which are xml based and some of them annotation based too like **Dozer** but I found the usage a bit complex.

Lets try to rewrite the above example using Omapper :

Now the question is where shall we put the mappings in source or target bean??

Logically the target should know where it needs to pull data from , which class , which property and think about the scenario where we need to collate data from multiple beans.. so Omapper works on the same philosophy...

So, lets redefine Bean2 definition a bit and decorate it with some magic annotations:
```
/**
 * The Class Bean2.
 */
@Mappable
public class Bean2 {

	/** The name. */
	@Source(type = org.omapper.test.Bean1.class, property = "name")
	private String name;
	
	/** The address. */
	@Source(type = Bean1.class, property = "address")
	private String address;
	
	/** The age. */
	@Source(type = Bean1.class, property = "age")
	private int age;
	
	/** The emp_id. */
	@Source(type = Bean1.class, property = "emp_id")
	private Integer emp_id;

}

```

Doesn't seem to different just a few annotations and no get-set clutter.
So, lets see what has been done.

There are two annotations defined by Omapper:
```
 @Mappable
```

> This annotation is a marker annotation to identify which beans are
> managed by OMapper.

```
 @Source(type = Bean1.class, property = "age")
```

This annotation defines the mapping.

Here,
type => defines which class the source property comes from,

property=> defines the name of the source property(might be a         different name than target property)


So the transformation code looks something like this:
```

Bean1 bean1=.... ;
Bean2 bean2=....;
//Initialize the SimpleMapper utility with the types of beans
SimpleMapper<Bean2, Bean1> mapper=new SimpleMapper<Bean2, Bean1>(Bean2.class,Bean1.class);

/*Method call which transforms beans based on the annotations
First argument specifies target Bean and second argument specifies the source bean*/
mapper.mapBean(bean2, bean1);

....


```


So, whenever any of the beans is updated above code do not change , only thing updated are the annotations.

Now, suppose Bean2 gets some more fields whose value comes from another Bean3 , so we need to collate data from two object viz. Bean1 and Bean3 to populate Bean2 object.

Say Bean3 has following structure:

```

public class Bean3 {
	
	/** The company name. */
	private String companyName;
	
	/** The positions list. */
	private List<String> positionsList;
}

```

New definition of Bean2 looks like this:
```

public class Bean2 {

	/** The name. */
	@Source(type = org.omapper.test.Bean1.class, property = "name")
	private String name;
	
	/** The address. */
	@Source(type = Bean1.class, property = "address")
	private String address;
	
	/** The age. */
	@Source(type = Bean1.class, property = "age")
	private int age;
	
	/** The emp_id. */
	@Source(type = Bean1.class, property = "emp_id")
	private Integer emp_id;

	/** The positions list. */
	@Source(type = Bean3.class, property = "positionsList")
	private List<String> positionsList;

	/** The company name. */
	@Source(type = Bean3.class, property = "companyName")
	private String companyName;
}
```


Now , to cater such scenarios the OMapper library provide another mapper class called CollatingMapper.

So, the transformation code now look like below:
```

//Source Beans
Bean1 bean1=...;
Bean3 bean3=...;

//Target Bean
Bean2 bean2=...;


CollatingMapper<Bean2> collatingMapper=new CollatingMapper<Bean2>(Bean2.class,Bean1.class, Bean3.class);
		
collatingMapper.mapBean(bean2, bean1,bean3);

```

Both the constructor and mapBean method in CollatingMapper support var args , so when the source bean count increases one can even pass an array of source beans.
First argument specifies the target bean, and second argument is a var arg parameter specifying the source beans.