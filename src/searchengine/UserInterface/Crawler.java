package searchengine.UserInterface;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import searchengine.indexer.Indexer;
import searchengine.spider.BreadthFirstSpider;
import searchengine.spider.PriorityBasedSpider;

/**
 *
 * @author Mnkey D.Luffy
 */
public class Crawler extends javax.swing.JFrame {
    
    public String result[];
    /** Creates new form Crawler */
    public Crawler() {
        initComponents();
    }
    public void initComponents()
    {
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/searchengine/UserInterface/Crawler.gif"))); // NOI18N
        jLabel1.setText("jLabel1");

        jTextField1.setText("Enter URL");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "list", "hash", "avl" }));

        jButton1.setText("Crawl");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField2.setText("Enter Limit");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap(153, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>   
    public void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    String url=jTextField1.getText();
    String file="index.txt";
    String limit=jTextField2.getText();
    Object dict=jComboBox1.getSelectedItem();
    if(url.compareTo("")==0)
    {
       jTextArea1.append("URL not valid!\n");
    }
    if(file.compareTo("")==0)
    {
        jTextArea1.append("File name not valid!\n");
    }
    if(limit.compareTo("")==0)
    {
        jTextArea1.append("Entered Limit not valid!\n");
    }
    else if(url!=""&&limit!="")
    {
        //jTextArea1.append(url+" "+file+" "+limit+" "+dict);
        try {
			URL u;
                        Crawler c=new Crawler();
			FileOutputStream isaveFile;
                        result=new String[Integer.parseInt(limit)+1];
			
                        PriorityBasedSpider web=null;
			
				u = new URL(url);
				String indexMode = dict.toString();
				indexMode = indexMode.toLowerCase();
				// hash - Dictionary Structure based on a Hashtable or HashMap from the Java collections
				// list - Dictionary Structure based on Linked List
				// myhash - Dictionary Structure based on a Hashtable implemented by the students
				// bst - Dictionary Structure based on a Binary Search Tree implemented by the students
				// avl - Dictionary Structure based on AVL Tree implemented by the students

				if (indexMode.equals("list") || indexMode.equals("hash") || indexMode.equals("myhash")
						|| indexMode.equals("bst") || indexMode.equals("avl"))
					web = new PriorityBasedSpider(u, new Indexer(indexMode));

				else {
					jTextArea1.append("Invalid index mode - use either \"list\" or \"hash\"");
					System.exit(1);
				}

				// Open the index save file
				isaveFile = new FileOutputStream(file);



				//Checking Crawl limit
				web.crawlLimitDefault = Integer.parseInt(limit);

			// Crawl the web site
			Indexer index = web.crawl();
                        jTextArea1.append("\nCrawling: "+url);
                        jTextArea1.append("\n*******************************************************");
                        for(int i=0;i<Integer.parseInt(limit)+1;i++)
                        {
                            if(result[i]!=null)
                            jTextArea1.append("\n"+(i+1)+"==>\t"+result[i]);
                        }
                      //  Object t=web.time;
                      //  jTextArea1.append("\n\nTime: "+t.toString()+"\n");
			// Save the index to the specified file
			index.save(isaveFile);
			
		} catch (IOException e) {
			jTextArea1.append("\nBad file or URL specification");
		} catch (NumberFormatException e) {
			jTextArea1.append("\nBad page limit.");
		}
    }
    }
     public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Crawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Crawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Crawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Crawler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Crawler().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    // End of variables declaration
    public javax.swing.JButton jButton1;
     public javax.swing.JComboBox jComboBox1;
       public javax.swing.JTextField jTextField1;
    public javax.swing.JTextField jTextField2;
       public javax.swing.JTextArea jTextArea1;
        public javax.swing.JScrollPane jScrollPane1;
          public javax.swing.JLabel jLabel1;
}
 