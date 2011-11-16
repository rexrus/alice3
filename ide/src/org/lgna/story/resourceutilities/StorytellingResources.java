package org.lgna.story.resourceutilities;


import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.alice.ide.IDE;
import org.alice.ide.ResourcePathManager;
import org.lgna.project.ast.JavaType;

import edu.cmu.cs.dennisc.javax.swing.models.TreeNode;

public class StorytellingResources {
	private static class SingletonHolder {
		private static StorytellingResources instance = new StorytellingResources();
	}
	public static StorytellingResources getInstance() {
		return SingletonHolder.instance;
	}
	
	private static final String SIMS_RESOURCE_INSTALL_PATH = "assets/sims";
	private static final String ALICE_RESOURCE_INSTALL_PATH = "assets/alice";
	
	private final ModelResourceTree galleryTree;
	private final List<File> simsPathsLoaded = new LinkedList<File>();
	
	
	private File findResourcePath(String relativePath)
	{
		File rootGallery = null;
		if (IDE.getActiveInstance() != null)
		{
			rootGallery = IDE.getActiveInstance().getGalleryRootDirectory();	
		}
		else
		{
			rootGallery =  IDE.getPathFromProperties( new String[] { "org.alice.ide.IDE.install.dir", "user.dir" }, new String[] { "gallery", "required/gallery/" + org.lgna.project.Version.getCurrentVersionText() } );
		}
		if (rootGallery != null && rootGallery.exists())
		{
			File path = new File(rootGallery, relativePath);
			if (path.exists())
			{
				return path;
			}
		}
		return null;
	}
	
	private List<File> findSimsBundles()
	{
		File simsPath = findResourcePath(SIMS_RESOURCE_INSTALL_PATH);
		if (simsPath != null)
		{
			ResourcePathManager.addPath(ResourcePathManager.SIMS_RESOURCE_KEY, simsPath);
			return ResourcePathManager.getPaths(ResourcePathManager.SIMS_RESOURCE_KEY);
		}
		else
		{
			return new LinkedList<File>();
		}
	}
	
	private List<File> findAliceResources()
	{
		File alicePath = findResourcePath(ALICE_RESOURCE_INSTALL_PATH);
		if (alicePath != null)
		{
			ResourcePathManager.addPath(ResourcePathManager.MODEL_RESOURCE_KEY, alicePath);
			return ResourcePathManager.getPaths(ResourcePathManager.MODEL_RESOURCE_KEY);
		}
		else
		{
			return new LinkedList<File>();
		}
	}
	
	private StorytellingResources(){
		List<File> resourcePaths = ResourcePathManager.getPaths(ResourcePathManager.MODEL_RESOURCE_KEY);
		if (resourcePaths.size() == 0)
		{
			resourcePaths = findAliceResources();
		}
		List<Class<? extends org.lgna.story.resources.ModelResource>> modelResourceClasses = ModelResourceUtilities.getAndLoadModelResourceClasses(resourcePaths);
		this.galleryTree = new ModelResourceTree(modelResourceClasses);
		if (resourcePaths.size() == 0)
		{
			javax.swing.JOptionPane.showMessageDialog( null, "Cannot find the Alice gallery resources." );
		}
	}
	
	
	public void loadSimsBundles()
	{
		List<File> resourcePaths = ResourcePathManager.getPaths(ResourcePathManager.SIMS_RESOURCE_KEY);
		if (resourcePaths.size() == 0)
		{
			resourcePaths = findSimsBundles();
		}
		for (File path : resourcePaths)
		{
			for( java.io.File file : path.listFiles() ) {
				if (!simsPathsLoaded.contains(file))
				{
					try {
						if( file.getName().endsWith( "txt" ) ) {
							//pass
						} else {
							edu.cmu.cs.dennisc.nebulous.Manager.addBundle( file );
							simsPathsLoaded.add(file);
						}
					} catch( Throwable t ) {
						t.printStackTrace();
					}
				}
			}
		}
		if (simsPathsLoaded.size() == 0){
			javax.swing.JOptionPane.showMessageDialog( null, "Cannot find The Sims (TM) 2 Art Assets." );
		}
	}
	
	public List< org.lgna.project.ast.JavaType > getTopLevelGalleryTypes() {
		List<ModelResourceTreeNode> rootNodes = this.galleryTree.getRootNodes();
		List< org.lgna.project.ast.JavaType > rootTypes = edu.cmu.cs.dennisc.java.util.Collections.newArrayList();
		for (ModelResourceTreeNode node : rootNodes)
		{
			rootTypes.add(node.getUserType().getFirstTypeEncounteredDeclaredInJava());
		}
		return rootTypes;
	}
	
	public  org.lgna.project.ast.AbstractType< ?, ?, ? > getGalleryResourceParentFor( org.lgna.project.ast.AbstractType< ?, ?, ? > type ) {
		TreeNode<JavaType> child = this.getGalleryResourceTreeNodeForJavaType(type);
		if (child != null)
		{
			ModelResourceTreeNode parent = (ModelResourceTreeNode)child.getParent();
			//Go up an extra level if the node we're returning is a node with a single child (this mirrors what is happening in getResourceChildren)
			if (parent != null && hasSingleLeafChild(parent)) {
				parent = (ModelResourceTreeNode)parent.getParent();
			}
			return parent.getResourceJavaType();
		}
		else
		{
			return null;
		}
	}
	
	private boolean hasSingleLeafChild(TreeNode<?> node) {
		return (node.getChildCount() == 1 && node.getChildAt(0).getChildCount() == 0);
	}
	
	public List<org.lgna.project.ast.AbstractDeclaration> getGalleryResourceChildrenFor( org.lgna.project.ast.AbstractType< ?, ?, ? > type ) 
	{
		System.out.println("Getting children for type: "+type);
		java.util.List<org.lgna.project.ast.AbstractDeclaration> toReturn = edu.cmu.cs.dennisc.java.util.Collections.newArrayList();
		TreeNode<JavaType> typeNode = this.getGalleryResourceTreeNodeForJavaType(type);
		
		for (int i=0; i<typeNode.getChildCount(); i++)
		{
			TreeNode<JavaType> child = typeNode.getChildAt(i);
			//If the child has a single leaf child, go down a level and return that
			if (hasSingleLeafChild(child)) {
				child = child.getChildAt(0);
			}
			ModelResourceTreeNode node = (ModelResourceTreeNode)child;
			if (node.isLeaf() && node.getJavaField() != null)
			{
				System.out.println("  Returning field: "+node.getJavaField());
				toReturn.add(node.getJavaField());
			}
			else
			{
				System.out.println("  Returning type: "+node.getResourceJavaType());
				toReturn.add(node.getResourceJavaType());
			}
		}
		return toReturn;
		
	}
	
	private ModelResourceTreeNode getGalleryResourceTreeNodeForJavaType( org.lgna.project.ast.AbstractType< ?, ?, ? > type ) 
	{
		return this.galleryTree.getGalleryResourceTreeNodeForJavaType(type);
	}
	
	public ModelResourceTreeNode getGalleryResourceTreeNodeForUserType( org.lgna.project.ast.AbstractType< ?, ?, ? > type ) 
	{
		return this.galleryTree.getGalleryResourceTreeNodeForUserType(type);
	}
	
	public ModelResourceTreeNode getGalleryTree()
	{
		return galleryTree.getTree();
	}
	

}
