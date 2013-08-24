//
//  MGMViewController.m
//  Music Geek Monthly
//
//  Created by Ceri Hughes on 23/06/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMViewController.h"

@interface MGMViewController () <UIPopoverControllerDelegate>

@property (strong) UIPopoverController* iPadPopoverController;

@end

@implementation MGMViewController

static BOOL userInterfaceIdiomIpad;

+ (void) initialize
{
    userInterfaceIdiomIpad = [UIDevice currentDevice].userInterfaceIdiom == UIUserInterfaceIdiomPad;
}

- (void) transitionCompleteWithState:(id)state
{
}

- (void) handleError:(NSError*)error
{
    [self.ui handleError:error];
}

- (void) presentViewModally:(UIView*)view sender:(id)sender
{
    if (userInterfaceIdiomIpad)
    {
        [self presentIpadViewModally:view sender:sender];
    }
    else
    {
        [self presentIphoneViewModally:view sender:sender];
    }
}

- (void) dismissModalPresentation
{
    if (userInterfaceIdiomIpad)
    {
        [self dismissIpadModalPresentation];
    }
    else
    {
        [self dismissIphoneModalPresentation];
    }
}

- (BOOL) isPresentingModally
{
    if (userInterfaceIdiomIpad)
    {
        return [self isIpadPresentingModally];
    }
    else
    {
        return [self isIphonePresentingModally];
    }
}

#pragma mark -
#pragma mark iPad modal presentation

- (void) presentIpadViewModally:(UIView*)view sender:(id)sender
{
    UIViewController* tempVC = [[UIViewController alloc] initWithNibName:nil bundle:nil];
    tempVC.view = view;
    self.iPadPopoverController = [[UIPopoverController alloc] initWithContentViewController:tempVC];
    self.iPadPopoverController.delegate = self;
    [self.iPadPopoverController presentPopoverFromBarButtonItem:(UIBarButtonItem*)sender permittedArrowDirections:UIPopoverArrowDirectionUp animated:YES];
}

- (void) dismissIpadModalPresentation
{
    [self.iPadPopoverController dismissPopoverAnimated:YES];
    [self popoverControllerDidDismissPopover:self.iPadPopoverController];
}

- (BOOL) isIpadPresentingModally
{
    return (self.iPadPopoverController != nil);
}

#pragma mark -
#pragma mark iPhone modal presentation

- (void) presentIphoneViewModally:(UIView*)view sender:(id)sender
{
    UIViewController* tempVC = [[UIViewController alloc] initWithNibName:nil bundle:nil];
    tempVC.view = view;

    [self presentViewController:tempVC animated:YES completion:NULL];
}

- (void) dismissIphoneModalPresentation
{
    [self dismissViewControllerAnimated:YES completion:NULL];
}

- (BOOL) isIphonePresentingModally
{
    return (self.presentedViewController != nil);
}

#pragma mark -
#pragma mark UIPopoverControllerDelegate

- (void) popoverControllerDidDismissPopover:(UIPopoverController*)popoverController
{
    self.iPadPopoverController = nil;
}

@end
